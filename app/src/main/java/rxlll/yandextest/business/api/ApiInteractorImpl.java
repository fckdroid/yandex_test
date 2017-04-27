package rxlll.yandextest.business.api;

import android.util.Log;
import android.util.Pair;

import com.google.gson.Gson;

import javax.inject.Inject;

import io.reactivex.Maybe;
import io.reactivex.Single;
import retrofit2.Response;
import rxlll.yandextest.App;
import rxlll.yandextest.data.network.models.dictionary.Dictionary;
import rxlll.yandextest.data.network.models.translator.Detect;
import rxlll.yandextest.data.network.models.translator.Langs;
import rxlll.yandextest.data.repositories.database.DatabaseRepository;
import rxlll.yandextest.data.repositories.database.Lang;
import rxlll.yandextest.data.repositories.database.Translation;
import rxlll.yandextest.data.repositories.dictionary.DictionaryRepository;
import rxlll.yandextest.data.repositories.preferences.PreferencesRepository;
import rxlll.yandextest.data.repositories.translator.TranslatorRepository;

import static rxlll.yandextest.App.LOG_TAG;
import static rxlll.yandextest.App.UI;

/**
 * Created by Maksim Sukhotski on 4/14/2017.
 */

public class ApiInteractorImpl implements ApiInteractor {

    @Inject
    TranslatorRepository translatorRepository;

    @Inject
    DictionaryRepository dictionaryRepository;

    @Inject
    DatabaseRepository databaseRepository;

    @Inject
    PreferencesRepository preferencesRepository;

    public ApiInteractorImpl() {
        App.appComponent.inject(this);
    }

    @Override
    public Maybe<Translation> translate(String text, Pair<Lang, Lang> dir) {
        String dirRequest = ((dir.first.getCode() != null) ? dir.first.getCode() + "-" : "") +
                dir.second.getCode();
        Single<Translation> remote = Single.zip(
                (dirRequest.length() > 2) ? translatorRepository.translate(text, dirRequest) :
                        Single.zip(translatorRepository.translate(text, dirRequest),
                                databaseRepository.getLangs(),
                                (translateResponse, langs) -> {
                                    for (Lang lang : langs) {
                                        if (translateResponse.body()
                                                .getDetected()
                                                .getLang()
                                                .equals(lang.getCode())) {
                                            translateResponse.body().getDetected().setLangPretty(lang);
                                            Log.d(LOG_TAG, "Ответ с определенным языком дополнен языком из БД");
                                            return translateResponse;
                                        }
                                    }
                                    return translateResponse;
                                }),
                dictionaryRepository.lookup(text.contains(" ") ? "" : text, dirRequest, UI),
                (translateResponse, dictionaryResponse) -> {
                    Log.d(LOG_TAG, "Пришли оба ответа с сервера");
                    Translation translation = new Translation();
                    translation.setOriginal(text);
                    translation.setDir(dir);
                    translation.setTranslateJsonResponse(new Gson().toJson(translateResponse.body()));
                    translation.setDictionaryJsonResponse(new Gson().toJson(dictionaryResponse.body()));
                    translation.setDirection(dirRequest);
                    if (dir.first.getId() == null) {
                        translation.setDir(new Pair<>(translateResponse.body().getDetected().getLangPretty(),
                                dir.second));
                    }
                    databaseRepository.putTranslation(translation).subscribe();
                    return translation;
                });
        return databaseRepository.getTranslation(text, dirRequest)
                .filter(translation -> translation.getOriginal() != null)
                .switchIfEmpty(remote.toMaybe());
    }

    @Override
    public Single<Response<Detect>> detect(String text) {
        return translatorRepository.detect(text);
    }

    @Override
    public Single<Response<Detect>> detect(String text, String hint) {
        return translatorRepository.detect(text, hint);
    }

    @Override
    public Maybe<Response<Langs>> getLangs(String ui) {
        Maybe<Response<Langs>> sourceRemote =
                translatorRepository.getLangs(ui)
                        .flatMap(langsResponse -> databaseRepository.putLangs(langsResponse)
                                .doOnSuccess(response -> {
                                    Log.d(LOG_TAG, "Языки из сети с дополненным телом из БД получены");
                                    preferencesRepository.putDirs(response.body().getDirs())
                                            .doOnComplete(() -> Log.d(LOG_TAG, "Направления записаны в preferences"))
                                            .subscribe();
                                }));

        return Single.zip(preferencesRepository.getDirs(), databaseRepository.getLangs(),
                (dirs, langs) -> {
                    Log.d(LOG_TAG, "Языки получены из БД");
                    return Response.success(new Langs(dirs, langs));
                })
                .filter(langsResponse -> {
                    if (langsResponse.body().getLangs().size() == 0 || langsResponse.body().getDirs().size() == 0) {
                        Log.d(LOG_TAG, "Но оказались пустыми");
                        return false;
                    }
                    return true;
                })
                .switchIfEmpty(sourceRemote);
    }

    @Override
    public Single<Response<Dictionary>> lookup(String text, String lang, String ui, String flags) {
        return dictionaryRepository.lookup(text, lang, ui, flags);
    }

    @Override
    public Single<Response<Dictionary>> lookup(String text, String lang, String ui) {
        return dictionaryRepository.lookup(text, lang, ui);
    }
}

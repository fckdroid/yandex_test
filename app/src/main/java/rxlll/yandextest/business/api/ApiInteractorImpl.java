package rxlll.yandextest.business.api;

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
import rxlll.yandextest.data.network.models.translator.Translate;
import rxlll.yandextest.data.repositories.database.DatabaseRepository;
import rxlll.yandextest.data.repositories.database.Lang;
import rxlll.yandextest.data.repositories.database.Translation;
import rxlll.yandextest.data.repositories.dictionary.DictionaryRepository;
import rxlll.yandextest.data.repositories.preferences.PreferencesRepository;
import rxlll.yandextest.data.repositories.translator.TranslatorRepository;

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
    public Maybe<Translation> translate(String text, Pair<Lang, Lang> direction, String lang) {
        Single<Response<Translate>> translationsSingle = (lang.length() > 2) ?
                translatorRepository.translate(text, lang) :
                translatorRepository.translate(text, lang)
                        .flatMap(translateResponse -> databaseRepository
                                .getLang(translateResponse.body().getDetected().getLang())
                                .flatMap(langObject -> {
                                    translateResponse.body().getDetected().setLangObject(langObject);
                                    return Single.just(translateResponse);
                                }));

        Single<Translation> remote = Single.zip(translationsSingle,
                dictionaryRepository.lookup(text.contains(" ") ? "" : text, lang, UI),
                (translateResponse, dictionaryResponse) -> {
                    Translation translation = new Translation();
                    translation.setTranslateJson(new Gson().toJson(translateResponse.body()));
                    translation.setDictionaryJson(new Gson().toJson(dictionaryResponse.body()));
                    translation.setOriginal(text);
                    translation.setDir(direction);
                    translation.setDirection(lang);
                    if (direction.first.getId() == null)
                        translation.setDir(new Pair<>(translateResponse.body()
                                .getDetected()
                                .getLangPretty(), direction.second));
                    return translation;
                })
                .doOnSuccess(translation -> databaseRepository.putTranslation(translation)
                        .subscribe());

        return databaseRepository.getTranslation(text, lang)
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
        Maybe<Response<Langs>> sourceRemote = translatorRepository.getLangs(ui)
                .flatMap(langsResponse -> databaseRepository.putLangs(langsResponse)
                        .doOnSuccess(response -> preferencesRepository.putDirections(response.body()
                                .getDirections()).subscribe()));

        return Single.zip(preferencesRepository.getDirections(), databaseRepository.getLangs(),
                (directions, langs) -> Response.success(new Langs(directions, langs)))
                .filter(langsResponse -> !(langsResponse.body().getLangs().size() == 0
                        || langsResponse.body().getDirections().size() == 0))
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

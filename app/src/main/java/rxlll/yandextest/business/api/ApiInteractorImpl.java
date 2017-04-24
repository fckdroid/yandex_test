package rxlll.yandextest.business.api;

import android.util.Log;

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
    public Maybe<Translation> translate(String text, String lang) {
        Single<Translation> remote = Single.zip(translatorRepository.translate(text, lang),
                dictionaryRepository.lookup(text.contains(" ") ? "" : text, lang, UI),
                (translateResponse, dictionaryResponse) -> {
                    Log.d(LOG_TAG, "Пришли оба ответа с сервера");
                    Translation translation = new Translation();
                    translation.setOriginal(text);
                    translation.setDir(lang);
                    translation.setTranslate(new Gson().toJson(translateResponse.body()));
                    translation.setDictionary(new Gson().toJson(dictionaryResponse.body()));
                    databaseRepository.putTranslation(translation).subscribe();
                    return translation;
                });
        return databaseRepository.getTranslation(text, lang)
                .filter(translation -> {
                    Log.d(LOG_TAG, "Фильтруем вывод из базы данных: " + String.valueOf(translation.getOriginal() != null));
                    return translation.getOriginal() != null;
                })
                .switchIfEmpty(remote.toMaybe());
    }


    @Override
    public Single<Response<Translate>> translate(String text, String lang, String options) {
        return translatorRepository.translate(text, lang, options);
    }

    @Override
    public Single<Response<Detect>> detect(String text) {
        return translatorRepository.detect(text);
    }

    @Override
    public Single<Response<Detect>> detect(String text, String hint) {
        return translatorRepository.detect(text, hint);
    }

    /**
     * On first app launch: Langs received locally ->
     * But were empty ->
     * Langs received remotely ->
     * Dirs were written to the preferences ->
     * Langs were written to the database;
     * <p>
     * Next time we will see only: Langs received locally.
     */
    @Override
    public Maybe<Response<Langs>> getLangs(String ui) {
        Maybe<Response<Langs>> sourceRemote = translatorRepository.getLangs(ui)
                .doOnSuccess(response -> {
                    Log.d(LOG_TAG, "Langs received remotely");
                    preferencesRepository.putDirs(response.body().getDirs())
                            .doOnComplete(() -> Log.d(LOG_TAG, "Dirs were written to the preferences"))
                            .subscribe();
                    databaseRepository.putLangs(response.body().getLangs())
                            .doOnComplete(() -> Log.d(LOG_TAG, "Langs were written to the database"))
                            .subscribe();
                });

        return Single.zip(preferencesRepository.getDirs(), databaseRepository.getLangs(),
                (dirs, langs) -> {
                    Log.d(LOG_TAG, "Langs received locally");
                    return Response.success(new Langs(dirs, langs));
                })
                .filter(langsResponse -> {
                    if (langsResponse.body().getLangs().size() == 0 || langsResponse.body().getDirs().size() == 0) {
                        Log.d(LOG_TAG, "But were empty");
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

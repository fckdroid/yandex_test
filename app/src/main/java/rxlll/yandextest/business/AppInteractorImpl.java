package rxlll.yandextest.business;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Single;
import retrofit2.Response;
import rxlll.yandextest.App;
import rxlll.yandextest.data.network.models.dictionary.Dictionary;
import rxlll.yandextest.data.network.models.translator.Detect;
import rxlll.yandextest.data.network.models.translator.Langs;
import rxlll.yandextest.data.network.models.translator.Translate;
import rxlll.yandextest.data.repositories.database.DatabaseRepository;
import rxlll.yandextest.data.repositories.database.Lang;
import rxlll.yandextest.data.repositories.dictionary.DictionaryRepository;
import rxlll.yandextest.data.repositories.preferences.PreferencesRepository;
import rxlll.yandextest.data.repositories.translator.TranslatorRepository;

/** Created by Maksim Sukhotski on 4/14/2017. */

public class AppInteractorImpl implements AppInteractor {

    @Inject
    TranslatorRepository translatorRepository;

    @Inject
    DictionaryRepository dictionaryRepository;

    @Inject
    DatabaseRepository databaseRepository;

    @Inject
    PreferencesRepository preferencesRepository;

    public AppInteractorImpl() {
        App.appComponent.inject(this);
    }

    @Override
    public Single<Response<Translate>> translate(String text, String lang) {
        return translatorRepository.translate(text, lang);
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

    @Override
    public Single<Response<Langs>> getLangs(String ui) {
        return translatorRepository.getLangs(ui);
    }

    @Override
    public Single<List<Lang>> getLangsLocal() {
        return databaseRepository.getLangs();
    }

    @Override
    public Single<Response<Dictionary>> lookup(String text, String lang, String ui, String flags) {
        return dictionaryRepository.lookup(text, lang, ui, flags);
    }

    @Override
    public Single<Response<Dictionary>> lookup(String text, String lang, String ui) {
        return dictionaryRepository.lookup(text, lang, ui);
    }

    @Override
    public Completable putLangs(Map<String, String> langs) {
        return databaseRepository.putLangs(langs);
    }

    @Override
    public Completable putRoutes(Set<String> langs) {
        return preferencesRepository.putRoutes(langs);
    }

    @Override
    public Single<Set<String>> getRoutes() {
        return preferencesRepository.getRoutes();
    }
}

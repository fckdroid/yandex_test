package rxlll.yandextest.data.repositories.translator;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Single;
import retrofit2.Response;
import rxlll.yandextest.App;
import rxlll.yandextest.data.network.TranslatorApi;
import rxlll.yandextest.data.network.models.Detect;
import rxlll.yandextest.data.network.models.Langs;
import rxlll.yandextest.data.network.models.Translate;

/** Created by Maksim Sukhotski on 4/14/2017. */

public class TranslatorRepositoryImpl implements TranslatorRepository {

    @Inject
    TranslatorApi translatorApi;

    public TranslatorRepositoryImpl() {
        App.appComponent.inject(this);
    }

    @Override
    public Single<Response<Translate>> translate(String text, String lang) {
        return translatorApi.translate(text, lang, null);
    }

    @Override
    public Single<Response<Translate>> translate(String text, String lang, String options) {
        return translatorApi.translate(text, lang, options);
    }

    @Override
    public Single<Response<Detect>> detect(String text) {
        return translatorApi.detect(text, null);
    }

    @Override
    public Single<Response<Detect>> detect(String text, String hint) {
        return translatorApi.detect(text, hint);
    }

    @Override
    public Single<Response<Langs>> getLangs(String ui) {
        return translatorApi.getLangs(ui);
    }

    @Override
    public Completable saveLangs(Langs langs) {
        return null;
    }
}

package rxlll.yandextest.data.repositories.translator;

import javax.inject.Inject;

import io.reactivex.Maybe;
import io.reactivex.Single;
import retrofit2.Response;
import rxlll.yandextest.App;
import rxlll.yandextest.data.network.api.TranslatorApi;
import rxlll.yandextest.data.network.models.translator.Detect;
import rxlll.yandextest.data.network.models.translator.Langs;
import rxlll.yandextest.data.network.models.translator.Translate;

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
    public Maybe<Response<Langs>> getLangs(String ui) {
        return translatorApi.getLangs(ui);
    }

//    @Override
//    public Completable putLangs(Langs langs) {
//        return null;
//    }
}

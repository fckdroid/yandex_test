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

    public static final int OPTION_AUTO_TRANSLATE_OFF = 0;
    public static final int OPTION_AUTO_TRANSLATE_ON = 1;
    @Inject
    TranslatorApi translatorApi;

    public TranslatorRepositoryImpl() {
        App.appComponent.inject(this);
    }

    @Override
    public Single<Response<Translate>> translate(String text, String lang) {
        return lang.length() > 2 ? translatorApi.translate(text, lang, OPTION_AUTO_TRANSLATE_OFF) :
                translatorApi.translate(text, lang, OPTION_AUTO_TRANSLATE_ON);
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
}

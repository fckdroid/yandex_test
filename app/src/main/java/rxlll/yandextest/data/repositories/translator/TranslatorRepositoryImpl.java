package rxlll.yandextest.data.repositories.translator;

import javax.inject.Inject;

import io.reactivex.Single;
import rxlll.yandextest.data.network.TranslatorApi;
import rxlll.yandextest.data.network.models.BaseResponse;
import rxlll.yandextest.data.network.models.Detect;
import rxlll.yandextest.data.network.models.Translate;

/** Created by Maksim Sukhotski on 4/14/2017. */

public class TranslatorRepositoryImpl implements TranslatorRepository {

    @Inject
    TranslatorApi translatorApi;

    @Override
    public Single<BaseResponse<Translate>> translate(String text, String lang) {
        return translatorApi.translate(text, lang, null);
    }

    @Override
    public Single<BaseResponse<Translate>> translate(String text, String lang, String options) {
        return translatorApi.translate(text, lang, options);
    }

    @Override
    public Single<BaseResponse<Detect>> detect(String text) {
        return translatorApi.detect(text, null);
    }

    @Override
    public Single<BaseResponse<Detect>> detect(String text, String hint) {
        return translatorApi.detect(text, hint);
    }

    @Override
    public Single<BaseResponse<Detect>> getLangs(String ui) {
        return translatorApi.getLangs(ui);
    }
}

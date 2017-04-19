package rxlll.yandextest.business.translator;

import io.reactivex.Single;
import rxlll.yandextest.data.network.models.BaseResponse;
import rxlll.yandextest.data.network.models.Detect;
import rxlll.yandextest.data.network.models.Translate;
import rxlll.yandextest.data.repositories.translator.TranslatorRepository;

/** Created by Maksim Sukhotski on 4/14/2017. */

public class TranslatorInteractorImpl implements TranslatorInteractor {

    private final TranslatorRepository translatorRepository;

    public TranslatorInteractorImpl(TranslatorRepository translatorRepository) {
        this.translatorRepository = translatorRepository;
    }

    @Override
    public Single<BaseResponse<Translate>> translate(String text, String lang) {
        return translatorRepository.translate(text, lang);
    }

    @Override
    public Single<BaseResponse<Translate>> translate(String text, String lang, String options) {
        return translatorRepository.translate(text, lang, options);
    }

    @Override
    public Single<BaseResponse<Detect>> detect(String text) {
        return translatorRepository.detect(text);
    }

    @Override
    public Single<BaseResponse<Detect>> detect(String text, String hint) {
        return translatorRepository.detect(text, hint);
    }

    @Override
    public Single<BaseResponse<Detect>> getLangs(String ui) {
        return translatorRepository.getLangs(ui);
    }
}

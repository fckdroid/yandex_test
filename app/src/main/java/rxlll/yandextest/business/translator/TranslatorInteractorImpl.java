package rxlll.yandextest.business.translator;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;
import rxlll.yandextest.App;
import rxlll.yandextest.data.network.models.dictionary.Dictionary;
import rxlll.yandextest.data.network.models.translator.Detect;
import rxlll.yandextest.data.network.models.translator.Langs;
import rxlll.yandextest.data.network.models.translator.Translate;
import rxlll.yandextest.data.repositories.dictionary.DictionaryRepository;
import rxlll.yandextest.data.repositories.translator.TranslatorRepository;

/** Created by Maksim Sukhotski on 4/14/2017. */

public class TranslatorInteractorImpl implements TranslatorInteractor {

    @Inject
    TranslatorRepository translatorRepository;

    @Inject
    DictionaryRepository dictionaryRepository;

    public TranslatorInteractorImpl() {
        App.appComponent.inject(this);
    }

    @Override
    public Single<Response<Translate>> translate(String text, String lang) {
        return translatorRepository.translate(text, lang)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Single<Response<Translate>> translate(String text, String lang, String options) {
        return translatorRepository.translate(text, lang, options)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Single<Response<Detect>> detect(String text) {
        return translatorRepository.detect(text)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Single<Response<Detect>> detect(String text, String hint) {
        return translatorRepository.detect(text, hint)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Single<Response<Langs>> getLangs(String ui) {
        return translatorRepository.getLangs(ui)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
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

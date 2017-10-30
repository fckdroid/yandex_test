package suhockii.dev.translator.data.repositories.dictionary;

import javax.inject.Inject;

import io.reactivex.Single;
import retrofit2.Response;
import suhockii.dev.translator.App;
import suhockii.dev.translator.data.network.api.DictionaryApi;
import suhockii.dev.translator.data.network.models.dictionary.Dictionary;

/**
 * Created by Maksim Sukhotski on 4/21/2017.
 */

public class DictionaryRepositoryImpl implements DictionaryRepository {

    @Inject
    DictionaryApi dictionaryApi;

    public DictionaryRepositoryImpl() {
        App.appComponent.inject(this);
    }

    @Override
    public Single<Response<Dictionary>> lookup(String text, String lang, String ui, String flags) {
        return dictionaryApi.lookup(text, lang, ui, flags);
    }

    @Override
    public Single<Response<Dictionary>> lookup(String text, String lang, String ui) {
        return dictionaryApi.lookup(text, lang, ui, null);
    }
}

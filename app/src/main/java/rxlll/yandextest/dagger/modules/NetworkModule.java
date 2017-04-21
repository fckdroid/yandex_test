package rxlll.yandextest.dagger.modules;

import android.support.annotation.NonNull;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import rxlll.yandextest.data.network.api.ApiService;
import rxlll.yandextest.data.network.api.DictionaryApi;
import rxlll.yandextest.data.network.api.TranslatorApi;

/** Created by Maksim Sukhotski on 4/14/2017. */

@Module
public class NetworkModule {

    private final String translatorApiUrl;
    private final String translatorApiKey;
    private final String dictionaryApiUrl;
    private final String dictionaryApiKey;

    public NetworkModule(@NonNull String translatorApiUrl,
                         @NonNull String translatorApiKey,
                         @NonNull String dictionaryApiUrl,
                         @NonNull String dictionaryApiKey) {
        this.translatorApiUrl = translatorApiUrl;
        this.translatorApiKey = translatorApiKey;
        this.dictionaryApiUrl = dictionaryApiUrl;
        this.dictionaryApiKey = dictionaryApiKey;
    }

    @Provides
    @NonNull
    @Singleton
    public TranslatorApi provideTranslatorApi() {
        return new ApiService(translatorApiUrl, translatorApiKey).build(TranslatorApi.class);
    }

    @Provides
    @NonNull
    @Singleton
    public DictionaryApi provideDictionaryApi() {
        return new ApiService(dictionaryApiUrl, dictionaryApiKey).build(DictionaryApi.class);
    }
}

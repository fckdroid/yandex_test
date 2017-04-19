package rxlll.yandextest.dagger.modules;

import android.support.annotation.NonNull;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import rxlll.yandextest.data.network.ApiService;
import rxlll.yandextest.data.network.TranslatorApi;

/** Created by Maksim Sukhotski on 4/14/2017. */

@Module
public class NetworkModule {

    private final String authToken;
    private final String baseUrl;

    public NetworkModule(@NonNull String baseUrl, @NonNull String authToken) {
        this.authToken = authToken;
        this.baseUrl = baseUrl;
    }

    @Provides
    @NonNull
    @Singleton
    public TranslatorApi provideTranslatorApi() {
        return new ApiService(baseUrl, authToken).build(TranslatorApi.class);
    }
}

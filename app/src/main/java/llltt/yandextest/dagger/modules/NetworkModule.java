package llltt.yandextest.dagger.modules;

import android.support.annotation.NonNull;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import llltt.yandextest.data.network.ApiService;
import llltt.yandextest.data.network.TranslatorApi;

/** Created by Maksim Sukhotski on 4/14/2017. */

@Module
public class NetworkModule {

    @Provides
    @NonNull
    @Singleton
    public TranslatorApi provideTranslatorApi() {
        return new ApiService().build(TranslatorApi.class);
    }
}

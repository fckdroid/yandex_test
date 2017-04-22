package rxlll.yandextest.di.modules;

import android.support.annotation.NonNull;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import rxlll.yandextest.business.api.ApiInteractor;
import rxlll.yandextest.business.api.ApiInteractorImpl;
import rxlll.yandextest.business.client.ClientInteractor;
import rxlll.yandextest.business.client.ClientInteractorImpl;

/** Created by Maksim Sukhotski on 4/14/2017. */

@Module
public final class InteractorsModule {

    @Provides
    @NonNull
    @Singleton
    public ApiInteractor provideApiInteractor() {
        return new ApiInteractorImpl();
    }

    @Provides
    @NonNull
    @Singleton
    public ClientInteractor provideClientInteractor() {
        return new ClientInteractorImpl();
    }
}

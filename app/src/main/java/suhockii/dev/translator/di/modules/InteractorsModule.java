package suhockii.dev.translator.di.modules;

import android.support.annotation.NonNull;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import suhockii.dev.translator.business.api.ApiInteractor;
import suhockii.dev.translator.business.api.ApiInteractorImpl;
import suhockii.dev.translator.business.client.ClientInteractor;
import suhockii.dev.translator.business.client.ClientInteractorImpl;

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

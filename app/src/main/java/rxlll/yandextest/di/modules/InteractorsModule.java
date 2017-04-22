package rxlll.yandextest.di.modules;

import android.support.annotation.NonNull;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import rxlll.yandextest.business.AppInteractor;
import rxlll.yandextest.business.AppInteractorImpl;

/** Created by Maksim Sukhotski on 4/14/2017. */

@Module
public final class InteractorsModule {

    @Provides
    @NonNull
    @Singleton
    public AppInteractor provideTranslatorInteractor() {
        return new AppInteractorImpl();
    }
}

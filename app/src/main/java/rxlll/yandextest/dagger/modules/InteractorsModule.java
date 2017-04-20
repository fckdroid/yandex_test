package rxlll.yandextest.dagger.modules;

import android.support.annotation.NonNull;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import rxlll.yandextest.business.preferences.PreferencesInteractor;
import rxlll.yandextest.business.preferences.PreferencesInteractorImpl;
import rxlll.yandextest.business.translator.TranslatorInteractor;
import rxlll.yandextest.business.translator.TranslatorInteractorImpl;
import rxlll.yandextest.data.repositories.preferences.PreferencesRepository;

/** Created by Maksim Sukhotski on 4/14/2017. */

@Module
public class InteractorsModule {

    @Provides
    @NonNull
    @Singleton
    public PreferencesInteractor providePreferencesInteractor(PreferencesRepository repository) {
        return new PreferencesInteractorImpl(repository);
    }

    @Provides
    @NonNull
    @Singleton
    public TranslatorInteractor provideTranslatorInteractor() {
        return new TranslatorInteractorImpl();
    }
}

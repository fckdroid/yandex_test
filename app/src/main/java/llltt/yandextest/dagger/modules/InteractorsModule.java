package llltt.yandextest.dagger.modules;

import android.support.annotation.NonNull;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import llltt.yandextest.business.preferences.PreferencesInteractor;
import llltt.yandextest.business.preferences.PreferencesInteractorImpl;
import llltt.yandextest.data.repositories.preferences.PreferencesRepository;

/** Created by Maksim Sukhotski on 4/14/2017. */

@Module
public class InteractorsModule {

    @Provides
    @NonNull
    @Singleton
    public PreferencesInteractor providePreferencesInteractor(PreferencesRepository repository) {
        return new PreferencesInteractorImpl(repository);
    }
//
//    @Provides
//    @NonNull
//    @Singleton
//    public DatabaseHelper getDatabaseHelper() {
//        return OpenHelperManager.getHelper(context, DatabaseHelper.class);
//    }
}

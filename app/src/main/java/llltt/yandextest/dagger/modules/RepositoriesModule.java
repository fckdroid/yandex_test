package llltt.yandextest.dagger.modules;

import android.content.Context;
import android.support.annotation.NonNull;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import llltt.yandextest.data.repositories.preferences.PreferencesRepository;
import llltt.yandextest.data.repositories.preferences.PreferencesRepositoryImpl;

/** Created by Maksim Sukhotski on 4/14/2017. */

@Module
public class RepositoriesModule {

    private Context context;

    public RepositoriesModule(@NonNull Context context) {
        this.context = context;
    }

    @Provides
    @NonNull
    @Singleton
    public PreferencesRepository providePreferencesRepository() {
        return new PreferencesRepositoryImpl(context);
    }
//
//    @Provides
//    @NonNull
//    @Singleton
//    public DatabaseHelper getDatabaseHelper() {
//        return OpenHelperManager.getHelper(appContext, DatabaseHelper.class);
//    }
}

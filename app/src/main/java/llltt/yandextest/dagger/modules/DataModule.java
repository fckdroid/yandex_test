package llltt.yandextest.dagger.modules;

import android.content.Context;
import android.support.annotation.NonNull;

import dagger.Module;

/** Created by Maksim Sukhotski on 4/14/2017. */

@Module
public class DataModule {

    private Context appContext;

    public DataModule(@NonNull Context appContext) {
        this.appContext = appContext;
    }

//    @Provides
//    @NonNull
//    @Singleton
//    public PreferencesWrapper getAppPreferences() {
//        return new PreferencesWrapper(appContext);
//    }
//
//    @Provides
//    @NonNull
//    @Singleton
//    public DatabaseHelper getDatabaseHelper() {
//        return OpenHelperManager.getHelper(appContext, DatabaseHelper.class);
//    }
}

package suhockii.dev.translator;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.frogermcs.androiddevmetrics.AndroidDevMetrics;

import org.greenrobot.greendao.database.Database;

import java.util.Locale;

import io.fabric.sdk.android.Fabric;
import suhockii.dev.translator.data.repositories.database.DaoMaster;
import suhockii.dev.translator.data.repositories.database.DaoSession;
import suhockii.dev.translator.di.AppComponent;
import suhockii.dev.translator.di.DaggerAppComponent;
import suhockii.dev.translator.di.modules.DatabaseModule;
import suhockii.dev.translator.di.modules.NetworkModule;
import suhockii.dev.translator.di.modules.RepositoriesModule;

import static suhockii.dev.translator.BuildConfig.DICTIONARY_API_KEY;
import static suhockii.dev.translator.BuildConfig.DICTIONARY_API_URL;
import static suhockii.dev.translator.BuildConfig.TRANSLATOR_API_KEY;
import static suhockii.dev.translator.BuildConfig.TRANSLATOR_API_URL;

//import com.squareup.leakcanary.RefWatcher;

/**
 * Created by Maksim Sukhotski on 4/14/2017.
 */

public class App extends Application {

    public static final String LOG_TAG = "app-logs";
    public static final String UI = Locale.getDefault().getLanguage();
    private static final String DATABASE_NAME = "app-database";
//    public static RefWatcher refWatcher;
    public static AppComponent appComponent;
    public static App instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        appComponent = DaggerAppComponent.builder()
                .repositoriesModule(new RepositoriesModule(this))
                .databaseModule(new DatabaseModule(getDaoSession()))
                .networkModule(new NetworkModule(TRANSLATOR_API_URL,
                        TRANSLATOR_API_KEY,
                        DICTIONARY_API_URL,
                        DICTIONARY_API_KEY))
                .build();
        if (BuildConfig.DEBUG) {
            AndroidDevMetrics.initWith(this);
//            Stetho.initialize(Stetho.newInitializerBuilder(this)
//                    .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
//                    .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
//                    .build());
//            if (!LeakCanary.isInAnalyzerProcess(this)) refWatcher = LeakCanary.install(this);
        }
        Fabric.with(this, new Crashlytics());
    }

    private DaoSession getDaoSession() {
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(this, DATABASE_NAME);
        Database database = devOpenHelper.getWritableDb();
        return new DaoMaster(database).newSession();
    }
}

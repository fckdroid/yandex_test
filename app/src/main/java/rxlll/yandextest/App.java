package rxlll.yandextest;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.frogermcs.androiddevmetrics.AndroidDevMetrics;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import org.greenrobot.greendao.database.Database;

import rxlll.yandextest.data.database.DaoMaster;
import rxlll.yandextest.data.database.DaoSession;
import rxlll.yandextest.di.AppComponent;
import rxlll.yandextest.di.DaggerAppComponent;
import rxlll.yandextest.di.modules.DatabaseModule;
import rxlll.yandextest.di.modules.NetworkModule;
import rxlll.yandextest.di.modules.RepositoriesModule;

import static rxlll.yandextest.BuildConfig.DICTIONARY_API_KEY;
import static rxlll.yandextest.BuildConfig.DICTIONARY_API_URL;
import static rxlll.yandextest.BuildConfig.TRANSLATOR_API_KEY;
import static rxlll.yandextest.BuildConfig.TRANSLATOR_API_URL;

/**
 * Created by Maksim Sukhotski on 4/14/2017.
 */

public class App extends Application {

    private static final String DATABASE_NAME = "app-database";

    public static RefWatcher refWatcher;
    public static AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
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
            Stetho.initialize(Stetho.newInitializerBuilder(this)
                    .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                    .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                    .build());
            if (!LeakCanary.isInAnalyzerProcess(this)) refWatcher = LeakCanary.install(this);
        }
    }

    private DaoSession getDaoSession() {
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(this, DATABASE_NAME);
        Database database = devOpenHelper.getWritableDb();
        return new DaoMaster(database).newSession();
    }
}

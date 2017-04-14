package llltt.yandextest;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.frogermcs.androiddevmetrics.AndroidDevMetrics;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

/** Created by Maksim Sukhotski on 4/14/2017. */

public class App extends Application {

    private static RefWatcher refWatcher;
    static App instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        if (BuildConfig.DEBUG) {
            AndroidDevMetrics.initWith(this);
            Stetho.initialize(Stetho.newInitializerBuilder(this)
                    .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                    .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                    .build());
            if (!LeakCanary.isInAnalyzerProcess(this)) refWatcher = LeakCanary.install(this);
        }
//        appComponent = DaggerAppComponent.builder()
//                .appModule(AppModule(this))
//                .build()
    }
}

package llltt.yandextest;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.frogermcs.androiddevmetrics.AndroidDevMetrics;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import llltt.yandextest.dagger.AppComponent;
import llltt.yandextest.dagger.DaggerAppComponent;
import llltt.yandextest.dagger.modules.RepositoriesModule;

/** Created by Maksim Sukhotski on 4/14/2017. */

public class App extends Application {

    public static RefWatcher refWatcher;
    static App instance;
    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        appComponent = DaggerAppComponent.builder()
                .repositoriesModule(new RepositoriesModule(this))
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
}

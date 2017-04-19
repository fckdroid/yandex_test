package rxlll.yandextest;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.frogermcs.androiddevmetrics.AndroidDevMetrics;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import rxlll.yandextest.dagger.DaggerAppComponent;
import rxlll.yandextest.dagger.modules.NetworkModule;
import rxlll.yandextest.dagger.modules.RepositoriesModule;

import static rxlll.yandextest.BuildConfig.API_KEY;
import static rxlll.yandextest.BuildConfig.API_URL;

/** Created by Maksim Sukhotski on 4/14/2017. */

public class App extends Application {

    public static RefWatcher refWatcher;

    @Override
    public void onCreate() {
        super.onCreate();
        DaggerAppComponent.builder()
                .repositoriesModule(new RepositoriesModule(this))
                .networkModule(new NetworkModule(API_URL, API_KEY))
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

package rxlll.yandextest;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.frogermcs.androiddevmetrics.AndroidDevMetrics;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import rxlll.yandextest.dagger.AppComponent;
import rxlll.yandextest.dagger.DaggerAppComponent;
import rxlll.yandextest.dagger.modules.NetworkModule;
import rxlll.yandextest.dagger.modules.RepositoriesModule;

import static rxlll.yandextest.BuildConfig.DICTIONARY_API_KEY;
import static rxlll.yandextest.BuildConfig.DICTIONARY_API_URL;
import static rxlll.yandextest.BuildConfig.TRANSLATOR_API_KEY;
import static rxlll.yandextest.BuildConfig.TRANSLATOR_API_URL;

/**
 * Created by Maksim Sukhotski on 4/14/2017.
 */

public class App extends Application {

    public static RefWatcher refWatcher;
    public static AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.builder()
                .repositoriesModule(new RepositoriesModule(this))
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
}

package rxlll.yandextest.dagger.modules;

import android.content.Context;
import android.support.annotation.NonNull;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import rxlll.yandextest.data.repositories.preferences.PreferencesRepository;
import rxlll.yandextest.data.repositories.preferences.PreferencesRepositoryImpl;
import rxlll.yandextest.data.repositories.translator.TranslatorRepository;
import rxlll.yandextest.data.repositories.translator.TranslatorRepositoryImpl;

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

    @Provides
    @NonNull
    @Singleton
    public TranslatorRepository provideTranslatorRepository() {
        return new TranslatorRepositoryImpl();
    }
}

package suhockii.dev.translator.di.modules;

import android.content.Context;
import android.support.annotation.NonNull;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import suhockii.dev.translator.data.repositories.database.DatabaseRepository;
import suhockii.dev.translator.data.repositories.database.DatabaseRepositoryImpl;
import suhockii.dev.translator.data.repositories.dictionary.DictionaryRepository;
import suhockii.dev.translator.data.repositories.dictionary.DictionaryRepositoryImpl;
import suhockii.dev.translator.data.repositories.preferences.PreferencesRepository;
import suhockii.dev.translator.data.repositories.preferences.PreferencesRepositoryImpl;
import suhockii.dev.translator.data.repositories.translator.TranslatorRepository;
import suhockii.dev.translator.data.repositories.translator.TranslatorRepositoryImpl;

/** Created by Maksim Sukhotski on 4/14/2017. */

@Module
public final class RepositoriesModule {

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
    public DatabaseRepository provideDatabaseRepository() {
        return new DatabaseRepositoryImpl();
    }

    @Provides
    @NonNull
    @Singleton
    public TranslatorRepository provideTranslatorRepository() {
        return new TranslatorRepositoryImpl();
    }

    @Provides
    @NonNull
    @Singleton
    public DictionaryRepository provideDictionaryRepository() {
        return new DictionaryRepositoryImpl();
    }
}

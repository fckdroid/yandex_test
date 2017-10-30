package suhockii.dev.translator.di.modules;

import android.support.annotation.NonNull;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import suhockii.dev.translator.data.repositories.database.DaoSession;
import suhockii.dev.translator.data.repositories.database.LangDao;
import suhockii.dev.translator.data.repositories.database.TranslationDao;

/**
 * Created by Maksim Sukhotski on 4/14/2017.
 */

@Module
public final class DatabaseModule {

    private final DaoSession daoSession;

    @Inject
    public DatabaseModule(@NonNull final DaoSession daoSession) {
        this.daoSession = daoSession;
    }

    @Provides
    @Singleton
    public LangDao provideLangDao() {
        return daoSession.getLangDao();
    }

    @Provides
    @Singleton
    public TranslationDao provideTranslationDao() {
        return daoSession.getTranslationDao();
    }
}

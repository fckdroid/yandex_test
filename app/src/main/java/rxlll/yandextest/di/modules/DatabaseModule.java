package rxlll.yandextest.di.modules;

import android.support.annotation.NonNull;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import rxlll.yandextest.data.repositories.database.DaoSession;
import rxlll.yandextest.data.repositories.database.LangDao;
import rxlll.yandextest.data.repositories.database.TranslationDao;

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

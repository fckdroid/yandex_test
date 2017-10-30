package suhockii.dev.translator.di;

import javax.inject.Singleton;

import dagger.Component;
import suhockii.dev.translator.business.api.ApiInteractorImpl;
import suhockii.dev.translator.business.client.ClientInteractorImpl;
import suhockii.dev.translator.data.repositories.database.DatabaseRepositoryImpl;
import suhockii.dev.translator.data.repositories.dictionary.DictionaryRepositoryImpl;
import suhockii.dev.translator.data.repositories.translator.TranslatorRepositoryImpl;
import suhockii.dev.translator.di.modules.DatabaseModule;
import suhockii.dev.translator.di.modules.InteractorsModule;
import suhockii.dev.translator.di.modules.NetworkModule;
import suhockii.dev.translator.di.modules.RepositoriesModule;
import suhockii.dev.translator.ui.langs.LangsPresenter;
import suhockii.dev.translator.ui.pager.PagerPresenter;
import suhockii.dev.translator.ui.pager.favorites.FavoritesPresenter;
import suhockii.dev.translator.ui.pager.history.HistoryPresenter;
import suhockii.dev.translator.ui.translator.TranslatorPresenter;

/**
 * Created by Maksim Sukhotski on 4/14/2017.
 */

@Component(modules = {RepositoriesModule.class,
        InteractorsModule.class,
        NetworkModule.class,
        DatabaseModule.class})
@Singleton
public interface AppComponent {
    void inject(TranslatorPresenter translatorPresenter);

    void inject(ApiInteractorImpl translatorInteractor);

    void inject(TranslatorRepositoryImpl translatorRepository);

    void inject(DictionaryRepositoryImpl dictionaryRepository);

    void inject(DatabaseRepositoryImpl databaseRepository);

    void inject(LangsPresenter langsPresenter);

    void inject(ClientInteractorImpl clientInteractor);

    void inject(HistoryPresenter historyPresenter);

    void inject(FavoritesPresenter favoritesPresenter);

    void inject(PagerPresenter pagerPresenter);
}

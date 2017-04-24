package rxlll.yandextest.di;

import javax.inject.Singleton;

import dagger.Component;
import rxlll.yandextest.business.api.ApiInteractorImpl;
import rxlll.yandextest.business.client.ClientInteractorImpl;
import rxlll.yandextest.data.repositories.database.DatabaseRepositoryImpl;
import rxlll.yandextest.data.repositories.dictionary.DictionaryRepositoryImpl;
import rxlll.yandextest.data.repositories.translator.TranslatorRepositoryImpl;
import rxlll.yandextest.di.modules.DatabaseModule;
import rxlll.yandextest.di.modules.InteractorsModule;
import rxlll.yandextest.di.modules.NetworkModule;
import rxlll.yandextest.di.modules.RepositoriesModule;
import rxlll.yandextest.ui.favorites.FavoritesPresenter;
import rxlll.yandextest.ui.history.HistoryPresenter;
import rxlll.yandextest.ui.langs.LangsPresenter;
import rxlll.yandextest.ui.translator.TranslatorPresenter;

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
}

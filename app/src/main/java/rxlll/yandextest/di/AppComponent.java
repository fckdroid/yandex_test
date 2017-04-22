package rxlll.yandextest.di;

import javax.inject.Singleton;

import dagger.Component;
import rxlll.yandextest.business.AppInteractorImpl;
import rxlll.yandextest.data.repositories.database.DatabaseRepositoryImpl;
import rxlll.yandextest.data.repositories.dictionary.DictionaryRepositoryImpl;
import rxlll.yandextest.data.repositories.translator.TranslatorRepositoryImpl;
import rxlll.yandextest.di.modules.DatabaseModule;
import rxlll.yandextest.di.modules.InteractorsModule;
import rxlll.yandextest.di.modules.NetworkModule;
import rxlll.yandextest.di.modules.RepositoriesModule;
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

    void inject(AppInteractorImpl translatorInteractor);

    void inject(TranslatorRepositoryImpl translatorRepository);

    void inject(DictionaryRepositoryImpl dictionaryRepository);

    void inject(DatabaseRepositoryImpl databaseRepository);
}

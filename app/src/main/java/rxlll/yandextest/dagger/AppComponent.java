package rxlll.yandextest.dagger;

import javax.inject.Singleton;

import dagger.Component;
import rxlll.yandextest.business.translator.TranslatorInteractorImpl;
import rxlll.yandextest.dagger.modules.InteractorsModule;
import rxlll.yandextest.dagger.modules.NetworkModule;
import rxlll.yandextest.dagger.modules.RepositoriesModule;
import rxlll.yandextest.data.repositories.translator.TranslatorRepositoryImpl;
import rxlll.yandextest.ui.translator.TranslatorPresenter;

/** Created by Maksim Sukhotski on 4/14/2017. */

@Component(modules = {RepositoriesModule.class, InteractorsModule.class, NetworkModule.class})
@Singleton
public interface AppComponent {
    void inject(TranslatorPresenter translatorPresenter);

    void inject(TranslatorInteractorImpl translatorInteractor);

    void inject(TranslatorRepositoryImpl translatorRepository);
}

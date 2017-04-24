package rxlll.yandextest.business.client;

import android.util.Pair;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Single;
import rxlll.yandextest.App;
import rxlll.yandextest.data.repositories.database.DatabaseRepository;
import rxlll.yandextest.data.repositories.database.Lang;
import rxlll.yandextest.data.repositories.database.Translation;
import rxlll.yandextest.data.repositories.preferences.PreferencesRepository;

/**
 * Created by Maksim Sukhotski on 4/23/2017.
 */

public class ClientInteractorImpl implements ClientInteractor {

    @Inject
    PreferencesRepository preferencesRepository;

    @Inject
    DatabaseRepository databaseRepository;

    public ClientInteractorImpl() {
        App.appComponent.inject(this);
    }

    @Override
    public Completable putAutoDetectSetting(boolean isTurnedOn) {
        return preferencesRepository.putAutoDetectSetting(isTurnedOn);
    }

    @Override
    public Single<Boolean> getAutoDetectSetting() {
        return preferencesRepository.getAutoDetectSetting();
    }

    @Override
    public Completable putDir(Pair<Lang, Lang> dir) {
        return preferencesRepository.putDir(dir);
    }

    @Override
    public Single<Pair<Lang, Lang>> getDir() {
        return preferencesRepository.getDir();
    }

    @Override
    public Completable putTranslationFavorite(Translation translation) {
        return databaseRepository.putTranslation(translation);
    }

    @Override
    public Single<List<Translation>> getTranslations() {
        return databaseRepository.getTranslations();
    }

}

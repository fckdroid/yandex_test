package rxlll.yandextest.business.client;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Single;
import rxlll.yandextest.App;
import rxlll.yandextest.data.repositories.preferences.PreferencesRepository;

/**
 * Created by Maksim Sukhotski on 4/23/2017.
 */

public class ClientInteractorImpl implements ClientInteractor {

    @Inject
    PreferencesRepository preferencesRepository;

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
}

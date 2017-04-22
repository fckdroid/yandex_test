package rxlll.yandextest.business.client;

import io.reactivex.Completable;
import io.reactivex.Single;

/**
 * Created by Maksim Sukhotski on 4/23/2017.
 */

public interface ClientInteractor {
    Completable putAutoDetectSetting(boolean isTurnedOn);

    Single<Boolean> getAutoDetectSetting();
}

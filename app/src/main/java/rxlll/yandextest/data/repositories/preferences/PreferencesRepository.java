package rxlll.yandextest.data.repositories.preferences;

import java.util.Set;

import io.reactivex.Completable;
import io.reactivex.Single;

/**
 * Created by Maksim Sukhotski on 4/14/2017.
 */

public interface PreferencesRepository {

    Completable putRoutes(Set<String> langs);

    Single<Set<String>> getRoutes();
}

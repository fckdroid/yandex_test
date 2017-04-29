package rxlll.yandextest.data.repositories.preferences;

import android.util.Pair;

import java.util.Set;

import io.reactivex.Completable;
import io.reactivex.Single;
import rxlll.yandextest.data.repositories.database.Lang;

/**
 * Created by Maksim Sukhotski on 4/14/2017.
 */

public interface PreferencesRepository {

    Completable putDirections(Set<String> langs);

    Single<Set<String>> getDirections();

    Completable putDir(Pair<Lang, Lang> dir);

    Single<Pair<Lang, Lang>> getDirection();

    Completable putAutoDetectSetting(boolean isTurnedOn);

    Single<Boolean> getAutoDetectSetting();


}

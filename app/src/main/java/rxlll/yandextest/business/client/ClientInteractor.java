package rxlll.yandextest.business.client;

import android.util.Pair;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import rxlll.yandextest.data.repositories.database.Lang;
import rxlll.yandextest.data.repositories.database.Translation;

/**
 * Created by Maksim Sukhotski on 4/23/2017.
 */

public interface ClientInteractor {
    Completable putAutoDetectSetting(boolean isTurnedOn);

    Single<Boolean> getAutoDetectSetting();

    Completable putDir(Pair<Lang, Lang> dir);

    Single<Pair<Lang, Lang>> getDir();

    Completable putTranslationFavorite(Translation translation);

    Single<List<Translation>> getTranslations(boolean favorites);
}

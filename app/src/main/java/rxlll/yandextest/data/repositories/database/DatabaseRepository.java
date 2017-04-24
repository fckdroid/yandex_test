package rxlll.yandextest.data.repositories.database;

import java.util.List;
import java.util.Map;

import io.reactivex.Completable;
import io.reactivex.Single;

/**
 * Created by Maksim Sukhotski on 4/21/2017.
 */

public interface DatabaseRepository {
    Completable putLangs(Map<String, String> langs);

    Single<List<Lang>> getLangs();

    Completable putTranslation(Translation translation);

    Single<List<Translation>> getTranslations(boolean favorites);

    Single<Translation> getTranslation(String text, String lang);
}

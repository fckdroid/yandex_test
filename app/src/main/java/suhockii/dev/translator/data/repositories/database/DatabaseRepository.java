package suhockii.dev.translator.data.repositories.database;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Single;
import retrofit2.Response;
import suhockii.dev.translator.data.network.models.translator.Langs;

/**
 * Created by Maksim Sukhotski on 4/21/2017.
 */

public interface DatabaseRepository {
    Maybe<Response<Langs>> putLangs(Response<Langs> langs);

    Single<List<Lang>> getLangs();

    Single<Lang> getLang(String code);

    Completable putTranslation(Translation translation);

    Single<List<Translation>> getTranslations(boolean favorites);

    Single<Translation> getTranslation(String text, String lang);

    Completable deleteAll();
}

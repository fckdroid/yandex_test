package rxlll.yandextest.data.repositories.translator;

import io.reactivex.Completable;
import io.reactivex.Single;
import retrofit2.Response;
import rxlll.yandextest.data.network.models.Detect;
import rxlll.yandextest.data.network.models.Langs;
import rxlll.yandextest.data.network.models.Translate;

/** Created by Maksim Sukhotski on 4/14/2017. */

public interface TranslatorRepository {

    Single<Response<Translate>> translate(String text,
                                          String lang);

    Single<Response<Translate>> translate(String text,
                                          String lang,
                                          String options);

    Single<Response<Detect>> detect(String text);

    Single<Response<Detect>> detect(String text,
                                    String hint);

    Single<Response<Langs>> getLangs(String ui);

    Completable saveLangs(Langs langs);
}

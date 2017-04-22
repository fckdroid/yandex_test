package rxlll.yandextest.business;

import java.util.Map;
import java.util.Set;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Single;
import retrofit2.Response;
import rxlll.yandextest.data.network.models.dictionary.Dictionary;
import rxlll.yandextest.data.network.models.translator.Detect;
import rxlll.yandextest.data.network.models.translator.Langs;
import rxlll.yandextest.data.network.models.translator.Translate;

/** Created by Maksim Sukhotski on 4/14/2017. */

public interface AppInteractor {

    Single<Response<Translate>> translate(String text,
                                          String lang);

    Single<Response<Translate>> translate(String text,
                                          String lang,
                                          String options);

    Single<Response<Detect>> detect(String text);

    Single<Response<Detect>> detect(String text,
                                    String hint);

    Maybe<Response<Langs>> getLangs(String ui);

//    Single<List<Lang>> getLangsLocal();

    Single<Response<Dictionary>> lookup(String text,
                                        String lang,
                                        String ui,
                                        String flags);

    Single<Response<Dictionary>> lookup(String text,
                                        String lang,
                                        String ui);

    Completable putLangs(Map<String, String> langs);

    Completable putDirs(Set<String> langs);

//    Single<Set<String>> getDirs();
}

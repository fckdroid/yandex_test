package rxlll.yandextest.business.translator;

import io.reactivex.Single;
import retrofit2.Response;
import rxlll.yandextest.data.network.models.dictionary.Dictionary;
import rxlll.yandextest.data.network.models.translator.Detect;
import rxlll.yandextest.data.network.models.translator.Langs;
import rxlll.yandextest.data.network.models.translator.Translate;

/** Created by Maksim Sukhotski on 4/14/2017. */

public interface TranslatorInteractor {

    Single<Response<Translate>> translate(String text,
                                          String lang);

    Single<Response<Translate>> translate(String text,
                                          String lang,
                                          String options);

    Single<Response<Detect>> detect(String text);

    Single<Response<Detect>> detect(String text,
                                    String hint);

    Single<Response<Langs>> getLangs(String ui);

    Single<Response<Dictionary>> lookup(String text,
                                        String lang,
                                        String ui,
                                        String flags);

    Single<Response<Dictionary>> lookup(String text,
                                        String lang,
                                        String ui);
}

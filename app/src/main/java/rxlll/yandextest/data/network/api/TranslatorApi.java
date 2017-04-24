package rxlll.yandextest.data.network.api;

import io.reactivex.Maybe;
import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rxlll.yandextest.data.network.models.translator.Detect;
import rxlll.yandextest.data.network.models.translator.Langs;
import rxlll.yandextest.data.network.models.translator.Translate;

/**
 * Created by Maksim Sukhotski on 4/14/2017.
 */

public interface TranslatorApi {

    @FormUrlEncoded
    @POST("translate")
    Single<Response<Translate>> translate(@Field("text") String text,
                                          @Field("lang") String lang,
                                          @Field("options") String options);

    @FormUrlEncoded
    @POST("detect")
    Single<Response<Detect>> detect(@Field("text") String text,
                                    @Field("hint") String hint);

    @FormUrlEncoded
    @POST("getLangs")
    Maybe<Response<Langs>> getLangs(@Field("ui") String ui);
}

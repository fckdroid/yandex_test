package llltt.yandextest.data.network;

import io.reactivex.Single;
import llltt.yandextest.data.network.models.BaseResponse;
import llltt.yandextest.data.network.models.Detect;
import llltt.yandextest.data.network.models.Translate;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/** Created by Maksim Sukhotski on 4/14/2017. */

public interface TranslatorApi {

    @FormUrlEncoded
    @POST("translate")
    Single<BaseResponse<Translate>> translate(@Field("text") String text,
                                              @Field("lang") String lang,
                                              @Field("options") String options);

    @FormUrlEncoded
    @POST("detect")
    Single<BaseResponse<Detect>> detect(@Field("text") String text,
                                        @Field("hint") String hint);

    @FormUrlEncoded
    @POST("getLangs")
    Single<BaseResponse<Detect>> getLangs(@Field("ui") String ui);
}

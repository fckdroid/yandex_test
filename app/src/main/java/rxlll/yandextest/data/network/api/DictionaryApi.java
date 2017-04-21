package rxlll.yandextest.data.network.api;

import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rxlll.yandextest.data.network.models.dictionary.Dictionary;

/**
 * Created by Maksim Sukhotski on 4/14/2017.
 */

public interface DictionaryApi {

    @FormUrlEncoded
    @POST("lookup")
    Single<Response<Dictionary>> lookup(@Field("lang") String text,
                                        @Field("text") String lang,
                                        @Field("ui") String ui,
                                        @Field("flags") String flags);
}

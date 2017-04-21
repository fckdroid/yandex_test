package rxlll.yandextest.data.network.api;

import com.facebook.stetho.okhttp3.StethoInterceptor;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rxlll.yandextest.data.network.interceptors.TokenInterceptor;

/** Created by Maksim Sukhotski on 4/14/2017. */

public class ApiService {

    private final String authToken;
    private final String baseUrl;

    public ApiService(String baseUrl, String apiKey) {
        this.baseUrl = baseUrl;
        this.authToken = apiKey;
    }

    public <S> S build(Class<S> serviceClass) {
        return this.build().create(serviceClass);
    }

    private Retrofit build() {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(createHttpClient())
                .build();
    }

    private OkHttpClient createHttpClient() {
        return new OkHttpClient.Builder()
                .addNetworkInterceptor(new StethoInterceptor())
                .addInterceptor(new TokenInterceptor(authToken))
                .build();
    }
}

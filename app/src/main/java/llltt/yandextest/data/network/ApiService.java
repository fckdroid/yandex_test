package llltt.yandextest.data.network;

import com.facebook.stetho.okhttp3.StethoInterceptor;

import llltt.yandextest.data.network.interceptors.AuthInterceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/** Created by Maksim Sukhotski on 4/14/2017. */

public class ApiService {

    public <S> S build(Class<S> serviceClass) {
        return this.build().create(serviceClass);
    }

    private Retrofit build() {
        String API_BASE_URL = "https://translate.yandex.net/api/v1.5/tr.json/";
        return new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(createHttpClient())
                .build();
    }

    private OkHttpClient createHttpClient() {
        String authToken = "trnsl.1.1.20170414T152323Z.1565788f9ca7097a.4cd8ac67900759868045bd80c0ac1a4f469b7dc2";
        return new OkHttpClient.Builder()
                .addNetworkInterceptor(new StethoInterceptor())
                .addInterceptor(new AuthInterceptor(authToken))
                .build();
    }
}

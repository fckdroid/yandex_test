package rxlll.yandextest.data.network.interceptors;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

/** Created by Maksim Sukhotski on 4/15/2017. */

public class TokenInterceptor implements Interceptor {
    private String apiKey;

    public TokenInterceptor(String token) {
        this.apiKey = token;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        RequestBody requestBody = request.body();
        String subtype = requestBody.contentType().subtype();
        if (subtype.contains("form")) {
            requestBody = processFormDataRequestBody(requestBody, apiKey);
        }
        if (requestBody != null) {
            Request.Builder requestBuilder = request.newBuilder();
            request = requestBuilder
                    .post(requestBody)
                    .build();
        }

        return chain.proceed(request);
    }

    private RequestBody processFormDataRequestBody(RequestBody requestBody, String token) {
        RequestBody formBody = new FormBody.Builder()
                .add("key", token)
                .build();
        String postBodyString = bodyToString(requestBody);
        postBodyString += ((postBodyString.length() > 0) ? "&" : "") + bodyToString(formBody);
        return RequestBody.create(requestBody.contentType(), postBodyString);
    }

    private String bodyToString(final RequestBody request) {
        try {
            final RequestBody copy = request;
            final Buffer buffer = new Buffer();
            if (copy != null)
                copy.writeTo(buffer);
            else
                return "";
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "did not work";
        }
    }
}


package suhockii.dev.translator.data.network.errors;

import com.google.gson.Gson;

import java.io.IOException;

import retrofit2.HttpException;
import retrofit2.Response;

public class RetrofitException extends RuntimeException {
    private final Response response;
    private final Kind kind;
    private String message;

    private RetrofitException(String message, Response response, Kind kind, Throwable exception) {
        super(message, exception);
        this.message = message;
        this.response = response;
        this.kind = kind;
    }

    public static RetrofitException createException(Throwable throwable) {

        if (throwable instanceof HttpException) {
            HttpException httpException = (HttpException) throwable;
            Response response = httpException.response();
            return new RetrofitException(response.message(), response, Kind.HTTP, throwable);
        }

        if (throwable instanceof IOException) {
            return new RetrofitException(throwable.getMessage(), null, Kind.NETWORK, throwable);
        }

        return new RetrofitException(throwable.getMessage(), null, Kind.UNEXPECTED, throwable);
    }

    public Response getResponse() {
        return response;
    }

    public Kind getKind() {
        return kind;
    }

    public ErrorsResponse getServerError() {
        if (response == null || response.errorBody() == null) {
            return null;
        }
        Gson gson = new Gson();
        ErrorsResponse serverError = null;

        try {
            serverError = gson.fromJson(response.errorBody().string(), ErrorsResponse.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return serverError;
    }

    public enum Kind {
        NETWORK,
        HTTP,
        UNEXPECTED
    }
}

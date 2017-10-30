package suhockii.dev.translator.data.network.errors;

public interface OnErrorConsumer {
    void onError(RetrofitException retrofitException);
}

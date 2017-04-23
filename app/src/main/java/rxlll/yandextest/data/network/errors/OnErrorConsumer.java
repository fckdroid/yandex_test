package rxlll.yandextest.data.network.errors;

public interface OnErrorConsumer {
    void onError(RetrofitException retrofitException);
}

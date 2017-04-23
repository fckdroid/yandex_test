package rxlll.yandextest.data.network.errors;

import io.reactivex.functions.Consumer;

public class ErrorConsumer implements Consumer<Throwable> {
    private OnErrorConsumer onErrorConsumer;

    public ErrorConsumer(OnErrorConsumer onErrorConsumer) {
        this.onErrorConsumer = onErrorConsumer;
    }

    @Override
    public void accept(Throwable throwable) throws Exception {
        onErrorConsumer.onError(RetrofitException.createException(throwable));
    }
}


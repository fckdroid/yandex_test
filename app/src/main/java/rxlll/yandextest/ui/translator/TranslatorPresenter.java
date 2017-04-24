package rxlll.yandextest.ui.translator;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.util.Pair;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import rxlll.yandextest.App;
import rxlll.yandextest.business.api.ApiInteractor;
import rxlll.yandextest.business.client.ClientInteractor;
import rxlll.yandextest.data.network.errors.ErrorConsumer;
import rxlll.yandextest.data.repositories.database.Lang;
import rxlll.yandextest.data.repositories.database.Translation;

import static rxlll.yandextest.App.UI;

/**
 * Created by Maksim Sukhotski on 4/16/2017.
 */

@InjectViewState
public class TranslatorPresenter extends MvpPresenter<TranslatorView> {

    @Inject
    ApiInteractor apiInteractor;

    @Inject
    ClientInteractor clientInteractor;

    private boolean dataReceived;

    public TranslatorPresenter(Context context) {
        App.appComponent.inject(this);
        dataReceived = true;
        updateNetworkState(context);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getData();
    }

    private void getData() {
        clientInteractor.getDir()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMapMaybe(langPair -> apiInteractor.getLangs(UI)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSuccess(langsResponse -> {
                            langPair.first.setDescription(langsResponse.body().getLangs().get(langPair.first.getCode()));
                            langPair.second.setDescription(langsResponse.body().getLangs().get(langPair.second.getCode()));
                            getViewState().showDirUpdated(langPair);
                            dataReceived = true;
                        }))
                .subscribe(langsResponse -> {
                        }, new ErrorConsumer(retrofitException -> {
                            dataReceived = false;
                            getViewState().showMessage("Проверьте подключение к сети");
                        })
                );
    }

    public void swapDir(Pair<Lang, Lang> dir) {
        if (dir == null) return;
        Pair<Lang, Lang> newDir = new Pair<>(dir.second, dir.first);
        clientInteractor.putDir(newDir)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
        getViewState().showDirUpdated(newDir);
//        getViewState().showDirWithoutAnim(newDir);
    }

    public void pushLangsController(boolean type, String currLang) {
        getViewState().showLangsController(type, currLang);
    }

    public void updateCurrentDir(Pair<Lang, Lang> dir) {
        if (dir == null) return;
        clientInteractor.putDir(dir)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
//        getViewState().showDirWithoutAnim(dir);
    }

    public void translateText(String text, Pair<Lang, Lang> dir) {
        if (text.isEmpty() || dir == null) return;
        String dirRequest = dir.first.getCode() + "-" + dir.second.getCode();
        apiInteractor.translate(text, dirRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(translateResponse -> getViewState().showTranslation(translateResponse),
                        new ErrorConsumer(retrofitException -> getViewState().showMessage("Проверьте подключение к сети")));
    }

    private void updateNetworkState(Context context) {
        BroadcastReceiver broadcastReceiver;
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (!dataReceived) getData();
            }
        };
        context.registerReceiver(broadcastReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    public void saveDirState(Pair<Lang, Lang> dir) {
        getViewState().showDirWithoutAnim(dir);
    }

    public void clearTranslatedText() {
        getViewState().showTranslation(new Translation());
    }
}

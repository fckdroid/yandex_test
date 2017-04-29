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
import retrofit2.Response;
import rxlll.yandextest.App;
import rxlll.yandextest.business.api.ApiInteractor;
import rxlll.yandextest.business.client.ClientInteractor;
import rxlll.yandextest.data.network.errors.ErrorConsumer;
import rxlll.yandextest.data.network.models.translator.Langs;
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

    TranslatorPresenter(Context context) {
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
        clientInteractor.getDirection()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMapMaybe(direction -> apiInteractor.getLangs(UI)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSuccess(langs -> makeDirections(direction, langs)))
                .subscribe(langsResponse -> {
                        }, new ErrorConsumer(retrofitException -> {
                            dataReceived = false;
                            getViewState().showMessage("Проверьте подключение к сети");
                        })
                );
    }

    private void makeDirections(Pair<Lang, Lang> direction, Response<Langs> langs) {
        if (langs.body().getLangsObject() == null) {
            String first = langs.body().getLangs().get(direction.first.getCode());
            String second = langs.body().getLangs().get(direction.second.getCode());
            direction.first.setDescription(first);
            direction.second.setDescription(second);
        } else {
            for (Lang lang : langs.body().getLangsObject()) {
                if (lang.getCode().equals(direction.first.getCode())) {
                    direction.first.setCode(lang.getCode());
                    direction.first.setDescription(lang.getDescription());
                    direction.first.setRating(lang.getRating());
                    direction.first.setId(lang.getId());
                }
                if (lang.getCode().equals(direction.second.getCode())) {
                    direction.second.setCode(lang.getCode());
                    direction.second.setDescription(lang.getDescription());
                    direction.second.setRating(lang.getRating());
                    direction.second.setId(lang.getId());
                }
            }
        }
        getViewState().showDirections(direction);
        getViewState().updateDirections(direction);
        dataReceived = true;
    }

    public void swapDir(Pair<Lang, Lang> dir) {
        if (dir == null) return;
        Pair<Lang, Lang> newDir = new Pair<>(dir.second, dir.first);
        clientInteractor.putDir(newDir)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
        getViewState().updateDirections(newDir);
    }

    public void pushLangsController(boolean type, Lang currLang) {
        getViewState().showLangsController(type, currLang);
    }

    public void updateCurrentDir(Pair<Lang, Lang> dir) {
        if (dir == null) return;
        getViewState().showDirections(dir);
        clientInteractor.putDir(dir)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    public void translate(String text, Pair<Lang, Lang> dir) {
        if (text.isEmpty() || dir == null) return;
        String dirRequest = ((dir.first.getCode() != null) ?
                dir.first.getCode() + "-" : "") + dir.second.getCode();
        apiInteractor.translate(text, dir, dirRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(translateResponse -> {
                            getViewState().showTranslation(translateResponse);
                            getViewState().showDirections(translateResponse.getDir());
                        },
                        new ErrorConsumer(retrofitException ->
                                getViewState().showMessage("Проверьте подключение к сети")));
    }

    void saveDirState(Pair<Lang, Lang> dir) {
        getViewState().showDirections(dir);
    }

    void clearTranslatedText() {
        getViewState().showTranslation(new Translation());
    }

    void setTranslateFavorite(Translation translation, boolean isFavorite) {
        translation.setIsFavorite(isFavorite);
        clientInteractor.putTranslationFavorite(translation)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
        getViewState().showTranslation(translation);
    }

    void setAutoDetect(boolean checked) {
        clientInteractor.putAutoDetectSetting(checked)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    private void updateNetworkState(Context context) {
        BroadcastReceiver broadcastReceiver;
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (!dataReceived) getData();
            }
        };
        context.registerReceiver(broadcastReceiver,
                new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }
}

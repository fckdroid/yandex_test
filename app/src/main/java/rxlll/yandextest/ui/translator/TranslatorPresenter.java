package rxlll.yandextest.ui.translator;

import android.support.annotation.NonNull;
import android.util.Pair;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;
import rxlll.yandextest.App;
import rxlll.yandextest.business.AppInteractor;
import rxlll.yandextest.data.network.models.translator.Langs;
import rxlll.yandextest.data.repositories.database.Lang;

/**
 * Created by Maksim Sukhotski on 4/16/2017.
 */

@InjectViewState
public class TranslatorPresenter extends MvpPresenter<TranslatorView> {

    @Inject
    AppInteractor appInteractor;
    private Langs langsFull;
    private String ui;
    public TranslatorPresenter() {
        App.appComponent.inject(this);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        langsFull = new Langs();
        ui = Locale.getDefault().getLanguage();

        appInteractor.getRoutes()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onRoutesReceivedLocal());
    }

    @NonNull
    private Consumer<Set<String>> onRoutesReceivedLocal() {
        return routes -> {
            if (routes.size() != 0) {
                langsFull.setDirs(routes);
                appInteractor.getLangsLocal()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(onLangsReceivedLocal());
            } else {
                appInteractor.getLangs(ui)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(onLangsReceivedRemote(ui));
            }
        };
    }

    @NonNull
    private Consumer<List<Lang>> onLangsReceivedLocal() {
        return langs -> {
            if (langs.size() != 0) {
                langsFull.setLangs(langs);
                getViewState().showRoute(new Pair<>(langsFull.getLangs().get(ui),
                        langsFull.getLangs().get(ui == "en" ? "ru" : "en")));
            } else {
                appInteractor.getLangs(ui)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(onLangsReceivedRemote(ui));
            }
        };
    }

    @NonNull
    private Consumer<Response<Langs>> onLangsReceivedRemote(String ui) {
        return response -> {
            appInteractor.putLangs(response.body().getLangs()).subscribe();
            appInteractor.putRoutes(response.body().getDirs()).subscribe();

            getViewState().showRoute(new Pair<>(response.body().getLangs().get(ui),
                    response.body().getLangs().get(ui == "en" ? "ru" : "en")));
        };
    }

    public void setRoute(Pair pair) {
        getViewState().showRoute(pair);
    }

    public void pushLangsController() {
        getViewState().showLangsController();
    }
}

package rxlll.yandextest.ui.translator;

import android.util.Pair;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import rxlll.yandextest.App;
import rxlll.yandextest.business.api.ApiInteractor;
import rxlll.yandextest.business.client.ClientInteractor;
import rxlll.yandextest.data.repositories.database.Lang;

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

    public TranslatorPresenter() {
        App.appComponent.inject(this);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        clientInteractor.getDir()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMapMaybe(langPair -> apiInteractor.getLangs(UI)
                        .doOnSuccess(langsResponse -> {
                            langPair.first.setDescription(langsResponse.body().getLangs().get(langPair.first.getCode()));
                            langPair.second.setDescription(langsResponse.body().getLangs().get(langPair.second.getCode()));
                            getViewState().showDirUpdated(langPair);
                        }))
                .subscribe();
    }

    public void setDir(Pair<Lang, Lang> dir) {
        clientInteractor.putDir(dir)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
        getViewState().showDirUpdated(dir);
    }

    public void pushLangsController(boolean type, String s) {
        getViewState().showLangsController(type, s);
    }

    public void updateCurrentDir(Pair<Lang, Lang> dir) {
        clientInteractor.putDir(dir)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
        getViewState().showDir(dir);
    }
}

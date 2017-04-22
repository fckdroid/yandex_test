package rxlll.yandextest.ui.translator;

import android.util.Pair;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.Locale;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import rxlll.yandextest.App;
import rxlll.yandextest.business.AppInteractor;

/**
 * Created by Maksim Sukhotski on 4/16/2017.
 */

@InjectViewState
public class TranslatorPresenter extends MvpPresenter<TranslatorView> {

    @Inject
    AppInteractor appInteractor;

    private String ui;

    public TranslatorPresenter() {
        App.appComponent.inject(this);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        ui = Locale.getDefault().getLanguage();

        appInteractor.getLangs(ui)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(langsResponse ->
                        getViewState().showDir(new Pair<>(langsResponse.body().getLangs().get(ui),
                                langsResponse.body().getLangs().get(ui == "en" ? "ru" : "en")))
                );
    }

    public void setDir(Pair pair) {
        getViewState().showDir(pair);
    }

    public void pushLangsController() {
        getViewState().showLangsController();
    }
}

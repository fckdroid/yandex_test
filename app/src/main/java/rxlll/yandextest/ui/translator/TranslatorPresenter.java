package rxlll.yandextest.ui.translator;

import android.util.Pair;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.Locale;

import javax.inject.Inject;

import rxlll.yandextest.App;
import rxlll.yandextest.business.translator.TranslatorInteractor;

/**
 * Created by Maksim Sukhotski on 4/16/2017.
 */

@InjectViewState
public class TranslatorPresenter extends MvpPresenter<TranslatorView> {

    @Inject
    TranslatorInteractor translatorInteractor;

    public TranslatorPresenter() {
        App.appComponent.inject(this);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        String ui = Locale.getDefault().getLanguage();
        translatorInteractor.getLangs(ui).subscribe(
                response -> getViewState().showRoute(new Pair<>(response.body().getLangs().get(ui),
                        response.body().getLangs().get(ui == "en" ? "ru" : "en"))),
                error -> {
                });
    }

    public void setRoute(Pair pair) {
        getViewState().showRoute(pair);
    }

    public void pushLangsController() {
        getViewState().showLangsController();
    }
}

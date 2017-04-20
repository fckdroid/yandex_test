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
                response -> getViewState().showCurrentRoute(new Pair<>(response.getLangs().get(ui),
                        response.getLangs().get(ui == "en" ? "ru" : "en"))),
                error -> {
                });
    }

    public void swapLangs() {
        getViewState().swapLangs();
    }
}

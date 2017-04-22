package rxlll.yandextest.ui.langs;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.bluelinelabs.conductor.Controller;

import java.util.Locale;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import rxlll.yandextest.App;
import rxlll.yandextest.business.AppInteractor;
import rxlll.yandextest.data.repositories.database.Lang;
import rxlll.yandextest.ui.translator.TranslatorController;

/**
 * Created by Maksim Sukhotski on 4/21/2017.
 */

@InjectViewState
public class LangsPresenter extends MvpPresenter<LangsView> {

    @Inject
    AppInteractor appInteractor;

    public LangsPresenter() {
        App.appComponent.inject(this);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        appInteractor.getLangs(Locale.getDefault().getLanguage())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(langsResponse ->
                        getViewState().showLangs(langsResponse.body().getLangsPretty()));

    }


    public void popController() {
        getViewState().popController();
    }

    public void setTitleText(boolean type) {
        getViewState().showTitleText(type);
    }

    public void setLang(boolean type, Lang lang, Controller targetController) {
        if (targetController != null) {
            ((TranslatorController) targetController).onLangPicked(type, lang.getDescription());
        }
        getViewState().popController();
    }
}

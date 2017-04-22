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

    public TranslatorPresenter() {
        App.appComponent.inject(this);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        String ui = Locale.getDefault().getLanguage();

        appInteractor.getLangs(ui)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(langsResponse ->//// TODO: 4/22/2017 cach lang for langcontroller
                        getViewState().showDirUpdated(new Pair<>(langsResponse.body().getLangs().get(ui),
                                langsResponse.body().getLangs().get(ui == "en" ? "ru" : "en")))
                );
    }

    public void setDir(Pair dir) {
        getViewState().showDirUpdated(dir);
    }

    public void pushLangsController(boolean type, String s) {
        getViewState().showLangsController(type, s);
    }

    public void saveCurrentDir(Pair<String, String> dir) {
        getViewState().showDir(dir);
    }
}

package rxlll.yandextest.ui.langs;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.bluelinelabs.conductor.Controller;

import java.util.Locale;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import rxlll.yandextest.App;
import rxlll.yandextest.business.api.ApiInteractor;
import rxlll.yandextest.business.client.ClientInteractor;
import rxlll.yandextest.data.network.errors.ErrorConsumer;
import rxlll.yandextest.data.repositories.database.Lang;
import rxlll.yandextest.ui.translator.TranslatorController;

/**
 * Created by Maksim Sukhotski on 4/21/2017.
 */

@InjectViewState
public class LangsPresenter extends MvpPresenter<LangsView> {

    @Inject
    ApiInteractor apiInteractor;

    @Inject
    ClientInteractor clientInteractor;

    public LangsPresenter() {
        App.appComponent.inject(this);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        apiInteractor.getLangs(Locale.getDefault().getLanguage())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(langsResponse ->
                                getViewState().showLangs(langsResponse.body().getLangsObject()),
                        new ErrorConsumer(retrofitException -> getViewState().showMessage("Проверьте подключение к сети")));
    }


    public void popController() {
        getViewState().popController();
    }

    public void setTitleText(boolean type) {
        getViewState().showTitleText(type);
    }

    public void setLang(boolean type, Lang lang, Controller targetController) {
        if (targetController != null)
            ((TranslatorController) targetController).onLangPicked(type, lang);
        getViewState().popController();
    }

    public void setAutoDetect(boolean type, boolean checked, Controller targetController) {
        clientInteractor.putAutoDetectSetting(checked)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete(() -> {
                    Lang lang = new Lang();
                    lang.setDescription("Определить");
                    if (checked) {
                        getViewState().popController();
                        ((TranslatorController) targetController).onLangPicked(type, lang);
                    }
                })
                .subscribe();
    }

    public void setSwitchState() {
        clientInteractor.getAutoDetectSetting()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(b -> getViewState().showSwitch(b));
    }
}

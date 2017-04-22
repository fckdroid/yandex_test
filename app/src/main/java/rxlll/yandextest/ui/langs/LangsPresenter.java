package rxlll.yandextest.ui.langs;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import javax.inject.Inject;

import rxlll.yandextest.business.AppInteractor;

/**
 * Created by Maksim Sukhotski on 4/21/2017.
 */

@InjectViewState
public class LangsPresenter extends MvpPresenter<LangsView> {


    @Inject
    AppInteractor appInteractor;

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
//        appInteractor.

    }


    public void popController() {
        getViewState().popController();
    }
}

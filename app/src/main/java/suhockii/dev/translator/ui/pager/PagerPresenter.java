package suhockii.dev.translator.ui.pager;

import com.arellomobile.mvp.MvpPresenter;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import suhockii.dev.translator.App;
import suhockii.dev.translator.business.client.ClientInteractor;

/**
 * Created by Maksim Sukhotski on 4/17/2017.
 */

public class PagerPresenter extends MvpPresenter<PagerView> {
    @Inject
    ClientInteractor clientInteractor;

    public PagerPresenter() {
        App.appComponent.inject(this);
    }

    void deleteAll() {
        clientInteractor.deleteAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }
}

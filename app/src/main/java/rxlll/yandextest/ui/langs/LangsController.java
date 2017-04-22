package rxlll.yandextest.ui.langs;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.bluelinelabs.conductor.Controller;

import rxlll.yandextest.R;
import rxlll.yandextest.ui.base.MoxyController;

/**
 * Created by Maksim Sukhotski on 4/21/2017.
 */

public class LangsController extends MoxyController implements LangsView {

    @InjectPresenter
    LangsPresenter langsPresenter;

    public LangsController() {
    }

    public <T extends Controller & TargetLangEntryControllerListener> LangsController(T targetController) {
        super();
        setTargetController(targetController);
    }

    @Override
    public void popController() {
        getRouter().popController(this);
    }

    @Override
    protected View inflateView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.controller_langs, container, false);
    }

    @Override
    protected void onViewBound(@NonNull View view) {
        super.onViewBound(view);
        view.findViewById(R.id.back_image_view).setOnClickListener(v -> {
            langsPresenter.popController();
        });
    }

    void onLangPicked() {
        Controller targetController = getTargetController();
        if (targetController != null) {
            ((TargetLangEntryControllerListener) targetController).onLangPicked("");
            getRouter().popController(this);
        }
    }

    public interface TargetLangEntryControllerListener {
        void onLangPicked(String lang);
    }
}

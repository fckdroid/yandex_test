package rxlll.yandextest.ui.langs;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import rxlll.yandextest.R;
import rxlll.yandextest.ui.base.MoxyController;

/**
 * Created by Maksim Sukhotski on 4/21/2017.
 */

public class LangsController extends MoxyController implements LangsView {
    @Override
    protected View inflateView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.controller_langs, container, false);
    }
}

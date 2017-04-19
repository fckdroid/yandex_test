package rxlll.yandextest.ui.translator;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import rxlll.yandextest.R;
import rxlll.yandextest.ui.base.MoxyController;

/**
 * Created by Maksim Sukhotski on 4/16/2017.
 */

public class TranslatorController extends MoxyController implements TranslatorView{

    @Override
    protected View inflateView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.controller_translator, container, false);
    }

    @Override
    protected void onViewBound(View view) {

    }
}

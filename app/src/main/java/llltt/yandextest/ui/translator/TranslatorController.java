package llltt.yandextest.ui.translator;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import llltt.yandextest.R;
import llltt.yandextest.ui.base.MoxyController;

/**
 * Created by Maksim Sukhotski on 4/16/2017.
 */

public class TranslatorController extends MoxyController {

    @Override
    protected View inflateView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.controller_translator, container, false);
    }

    @Override
    protected void onViewBound(View view) {

    }
}

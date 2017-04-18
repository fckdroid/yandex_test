package llltt.yandextest.ui.settings;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import llltt.yandextest.R;
import llltt.yandextest.ui.base.MoxyController;
import llltt.yandextest.ui.translator.TranslatorView;

/**
 * Created by Maksim Sukhotski on 4/17/2017.
 */

public class SettingsController extends MoxyController implements TranslatorView {
    @Override
    protected View inflateView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.controller_settings, container, false);
    }

    @Override
    protected void onViewBound(View view) {

    }
}

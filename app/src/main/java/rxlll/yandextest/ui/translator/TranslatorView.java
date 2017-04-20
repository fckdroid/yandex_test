package rxlll.yandextest.ui.translator;

import android.util.Pair;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

/**
 * Created by Maksim Sukhotski on 4/16/2017.
 */

@StateStrategyType(SkipStrategy.class)
public interface TranslatorView extends MvpView {

    void showCurrentRoute(Pair<String, String> route);

    void swapLangs();
}

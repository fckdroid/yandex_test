package rxlll.yandextest.ui.translator;

import android.util.Pair;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

/**
 * Created by Maksim Sukhotski on 4/16/2017.
 */

@StateStrategyType(AddToEndSingleStrategy.class)
public interface TranslatorView extends MvpView {

    void showDir(Pair<String, String> route);

//    void swapRoute(Pair<String, String> route);

    @StateStrategyType(SkipStrategy.class)
    void showLangsController();
}

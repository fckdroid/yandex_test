package rxlll.yandextest.ui.langs;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import java.util.List;

import rxlll.yandextest.data.repositories.database.Lang;

/**
 * Created by Maksim Sukhotski on 4/21/2017.
 */
@StateStrategyType(AddToEndSingleStrategy.class)
public interface LangsView extends MvpView {
    void popController();

    void showLangs(List<Lang> langs);

    void showTitleText(boolean type);

    void showSwitch(boolean b);
}

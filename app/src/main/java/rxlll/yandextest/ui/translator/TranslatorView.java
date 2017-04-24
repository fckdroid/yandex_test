package rxlll.yandextest.ui.translator;

import android.util.Pair;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import rxlll.yandextest.data.repositories.database.Lang;
import rxlll.yandextest.data.repositories.database.Translation;

/**
 * Created by Maksim Sukhotski on 4/16/2017.
 */

@StateStrategyType(AddToEndSingleStrategy.class)
public interface TranslatorView extends MvpView {

    @StateStrategyType(SkipStrategy.class)
    void showDirUpdated(Pair<Lang, Lang> dir);

    void showDirWithoutAnim(Pair<Lang, Lang> dir);

    @StateStrategyType(SkipStrategy.class)
    void showLangsController(boolean type, String s);

    void showTranslation(Translation translation);

    @StateStrategyType(SkipStrategy.class)
    void showMessage(String localizedMessage);

}

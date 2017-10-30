package suhockii.dev.translator.ui.translator;

import android.util.Pair;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import suhockii.dev.translator.data.repositories.database.Lang;
import suhockii.dev.translator.data.repositories.database.Translation;

/**
 * Created by Maksim Sukhotski on 4/16/2017.
 */

@StateStrategyType(SkipStrategy.class)
public interface TranslatorView extends MvpView {

    @StateStrategyType(AddToEndSingleStrategy.class)
    void showDirections(Pair<Lang, Lang> dir);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void showTranslation(Translation translation);

    void updateDirections(Pair<Lang, Lang> dir);

    void showLangsController(boolean type, Lang s);

    void showMessage(String localizedMessage);

}

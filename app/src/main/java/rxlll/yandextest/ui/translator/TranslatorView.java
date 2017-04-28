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

@StateStrategyType(SkipStrategy.class)
public interface TranslatorView extends MvpView {

    void updateDirections(Pair<Lang, Lang> dir);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void showDirections(Pair<Lang, Lang> dir);

    void showLangsController(boolean type, Lang s);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void showTranslation(Translation translation);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void showTranslationFavorite(boolean isFavorite);

    void showMessage(String localizedMessage);

}

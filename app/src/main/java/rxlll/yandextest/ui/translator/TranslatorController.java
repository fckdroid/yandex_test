package rxlll.yandextest.ui.translator;

import android.content.Context;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.bluelinelabs.conductor.RouterTransaction;
import com.bluelinelabs.conductor.changehandler.VerticalChangeHandler;

import rxlll.yandextest.App;
import rxlll.yandextest.R;
import rxlll.yandextest.data.network.models.dictionary.Dictionary;
import rxlll.yandextest.data.repositories.database.Lang;
import rxlll.yandextest.data.repositories.database.Translation;
import rxlll.yandextest.ui.base.MoxyController;
import rxlll.yandextest.ui.langs.LangsController;

/**
 * Created by Maksim Sukhotski on 4/16/2017.
 */

public class TranslatorController extends MoxyController implements TranslatorView, LangsController.TargetLangEntryControllerListener {

    public static final boolean TYPE_L = false;
    public static final boolean TYPE_R = true;
    @InjectPresenter
    TranslatorPresenter translatorPresenter;
    private TextView leftTextView;
    private TextView rightTextView;
    private View navigationView;
    private View swapImageView;
    private TextView copyRightTextView;
    private TextView translatorEditText;
    private Pair<Lang, Lang> dir;
    private Button translateButton;

    @ProvidePresenter
    TranslatorPresenter translatorPresenter() {
        return new TranslatorPresenter(App.instance.getApplicationContext());
    }

    @Override
    protected View inflateView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.controller_translator, container, false);
    }

    @Override
    protected void onViewBound(View view) {
        leftTextView = (TextView) view.findViewById(R.id.lang_left_text_view);
        rightTextView = (TextView) view.findViewById(R.id.lang_right_text_view);
        swapImageView = view.findViewById(R.id.swap_image_view);
        copyRightTextView = ((TextView) view.findViewById(R.id.copyright_text_view));
        translatorEditText = ((TextView) view.findViewById(R.id.translator_edit_text));
        translateButton = ((Button) view.findViewById(R.id.button));

        translateButton.setOnClickListener(v -> {
            closeKeyboard();
            translatorPresenter.translateText(translatorEditText.getText().toString(), dir);
        });
        navigationView = getActivity().findViewById(R.id.navigation);

        leftTextView.setOnClickListener(v ->
                translatorPresenter.pushLangsController(TYPE_L, leftTextView.getText().toString()));
        rightTextView.setOnClickListener(v ->
                translatorPresenter.pushLangsController(TYPE_R, rightTextView.getText().toString()));

        swapImageView.setOnClickListener(v ->
                translatorPresenter.swapDir(dir));
        copyRightTextView.setText(Html.fromHtml(getActivity().getString(R.string.translateFragment_copyright)));
        copyRightTextView.setMovementMethod(LinkMovementMethod.getInstance());

        Animation animNavHide = AnimationUtils.loadAnimation(getActivity(), R.anim.nav_up);
        animNavHide.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                navigationView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        navigationView.startAnimation(animNavHide);
    }

    @Override
    public void showDirUpdated(Pair<Lang, Lang> dir) {
        this.dir = dir;
        swapImageView.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.rotate));
        Animation animLeftToCenter = AnimationUtils.loadAnimation(getActivity(), R.anim.swap_left_to_center);
        Animation animCenterToLeft = AnimationUtils.loadAnimation(getActivity(), R.anim.swap_center_to_left);
        Animation animRightToCenter = AnimationUtils.loadAnimation(getActivity(), R.anim.swap_right_to_center);
        Animation animCenterToRight = AnimationUtils.loadAnimation(getActivity(), R.anim.swap_center_to_right);

        animLeftToCenter.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                leftTextView.setText(dir.first.getDescription());
                leftTextView.startAnimation(animCenterToLeft);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        animRightToCenter.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                rightTextView.setText(dir.second.getDescription());
                rightTextView.startAnimation(animCenterToRight);
                translatorPresenter.updateCurrentDir(dir);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        leftTextView.startAnimation(animLeftToCenter);
        rightTextView.startAnimation(animRightToCenter);
    }

    @Override
    public void showDir(Pair<Lang, Lang> dir) {
        this.dir = dir;
        leftTextView.setText(dir.first.getDescription());
        rightTextView.setText(dir.second.getDescription());
        if (dir.first.getDescription() == "Определить") {
            translatorEditText.setHint("Введите текст");
            swapImageView.setClickable(false);
            swapImageView.setBackground(getResources().getDrawable(R.drawable.circle_gray));
        } else {
            translatorEditText.setHint("Введите текст (" + dir.first.getDescription() + ")");
            swapImageView.setClickable(true);
        }
    }

    @Override
    public void showLangsController(boolean type, String currLang) {
        closeKeyboard();

        Animation animNavHide = AnimationUtils.loadAnimation(getActivity(), R.anim.nav_down);
        animNavHide.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                navigationView.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        navigationView.startAnimation(animNavHide);
        getRouter().pushController(RouterTransaction.with(new LangsController(this, type, currLang))
                .pushChangeHandler(new VerticalChangeHandler())
                .popChangeHandler(new VerticalChangeHandler()));
    }

    @Override
    public void showTranslation(Translation translation) {

    }

    @Override
    public void showMessage(String localizedMessage) {
        Toast.makeText(getActivity(), localizedMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showDictionaryData(Dictionary body) {

    }

    private void closeKeyboard() {
        ((InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getView().getWindowToken(), 0);
    }


    @Override
    public void onLangPicked(boolean type, Lang lang) {
        if (type == TYPE_L) {
            leftTextView.setText(lang.getDescription());
            dir.first.setDescription(lang.getDescription());
            dir.first.setCode(lang.getCode());
            dir.first.setRating(lang.getRating());
            translatorPresenter.updateCurrentDir(new Pair<>(dir.first, dir.second));
        } else {
            rightTextView.setText(lang.getDescription());
            dir.second.setDescription(lang.getDescription());
            dir.second.setCode(lang.getCode());
            dir.second.setRating(lang.getRating());
            translatorPresenter.updateCurrentDir(new Pair<>(dir.first, dir.second));
        }
    }
}

package rxlll.yandextest.ui.translator;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.bluelinelabs.conductor.RouterTransaction;
import com.bluelinelabs.conductor.changehandler.VerticalChangeHandler;

import rxlll.yandextest.App;
import rxlll.yandextest.R;
import rxlll.yandextest.data.repositories.database.Lang;
import rxlll.yandextest.data.repositories.database.Translation;
import rxlll.yandextest.ui.base.MoxyController;
import rxlll.yandextest.ui.langs.LangsController;

/**
 * Created by Maksim Sukhotski on 4/16/2017.
 */

public class TranslatorController extends MoxyController implements TranslatorView,
        LangsController.TargetLangEntryControllerListener, TextWatcherAdapter.TextWatcherListener {

    public static final boolean TYPE_L = false;
    public static final boolean TYPE_R = true;
    public static final int MAX_LETTERS = 10000;
    @InjectPresenter
    TranslatorPresenter translatorPresenter;
    private TextView leftTextView;
    private TextView rightTextView;
    private View navigationView;
    private View swapImageView;
    private TextView copyRightTextView;
    private EditText translatorEditText;
    private TextInputLayout textInputLayout;
    private Pair<Lang, Lang> dir;
    private Button translateButton;
    private TextView translateHeaderTextView;
    private TextView translateDescrTextView;
    private LinearLayout buttonContainer;
    private LinearLayout translatedLinearLayout;
    private Animation animButtonDown;
    private Animation animButtonUp;
    private Animation animFading;
    private Animation animFadingForRepeat;
    private boolean animIsNotRun;
    private Animation animFadingInvert;
    private boolean translationUpdated;
    private Animation animNavHide;
    private Animation animLeftToCenter;
    private Animation animCenterToLeft;
    private Animation animRightToCenter;
    private Animation animCenterToRight;
    private Translation translation;
    private ImageView favoriteImageView;

    @ProvidePresenter
    TranslatorPresenter translatorPresenter() {
        return new TranslatorPresenter(App.instance.getApplicationContext());
    }

    @Override
    protected View inflateView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.controller_translator, container, false);
    }

    @Override
    protected void onDetach(@NonNull View view) {
        super.onDetach(view);
        getActivity().findViewById(R.id.translator_container).setVisibility(View.GONE);
    }

    @Override
    protected void onViewBound(View view) {
        getActivity().findViewById(R.id.translator_container).setVisibility(View.VISIBLE);
        leftTextView = (TextView) view.findViewById(R.id.lang_left_text_view);
        rightTextView = (TextView) view.findViewById(R.id.lang_right_text_view);
        swapImageView = view.findViewById(R.id.swap_image_view);
        copyRightTextView = ((TextView) view.findViewById(R.id.copyright_text_view));
        translatorEditText = ((EditText) view.findViewById(R.id.translator_edit_text));
        translateHeaderTextView = ((TextView) view.findViewById(R.id.translate_header_text_view));
        translateDescrTextView = ((TextView) view.findViewById(R.id.translate_descr_text_view));
        textInputLayout = ((TextInputLayout) view.findViewById(R.id.text_container));
        translateButton = ((Button) view.findViewById(R.id.button));
        buttonContainer = ((LinearLayout) view.findViewById(R.id.button_container));
        translatedLinearLayout = ((LinearLayout) view.findViewById(R.id.translated_linear_layout));
        navigationView = getActivity().findViewById(R.id.navigation);
        favoriteImageView = (ImageView) view.findViewById(R.id.favorite_image_view);

        copyRightTextView.setText(Html.fromHtml(getActivity().getString(R.string.translateFragment_copyright)));
        copyRightTextView.setMovementMethod(LinkMovementMethod.getInstance());

        translatorEditText.addTextChangedListener(new TextWatcherAdapter(translatorEditText, this));

        favoriteImageView.setOnClickListener(v -> {
            if (translationUpdated) {
                boolean wasFavorite = favoriteImageView.getDrawable().getConstantState() ==
                        getResources().getDrawable(R.drawable.star_full).getConstantState();
                translatorPresenter.setTranslateFavorite(translation, !wasFavorite);
            }
        });
        leftTextView.setOnClickListener(v ->
                translatorPresenter.pushLangsController(TYPE_L, leftTextView.getText().toString()));
        rightTextView.setOnClickListener(v ->
                translatorPresenter.pushLangsController(TYPE_R, rightTextView.getText().toString()));
        swapImageView.setOnClickListener(v ->
                translatorPresenter.swapDir(dir));
        copyRightTextView.setText(Html.fromHtml(getActivity().getString(R.string.translateFragment_copyright)));
        copyRightTextView.setMovementMethod(LinkMovementMethod.getInstance());

        translateButton.setOnClickListener(v -> {
            translatorPresenter.translateText(translatorEditText.getText().toString(), dir);
            closeKeyboard();

        });

        translatorEditText.setOnTouchListener((v, event) -> {
            if (v.getId() == R.id.translator_edit_text) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                switch (event.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_UP:
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }
            }
            return false;
        });
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

        hideTheHorror();
        navigationView.startAnimation(animNavHide);
    }

    @Override
    public void showDirUpdated(Pair<Lang, Lang> dir) {
        this.dir = dir;
        swapImageView.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.rotate));
        leftTextView.startAnimation(animLeftToCenter);
        rightTextView.startAnimation(animRightToCenter);

    }

    @Override
    public void showDirWithoutAnim(Pair<Lang, Lang> dir) {
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
        if (translation.isNotEmpty()) {
            this.translation = translation;
            translationUpdated = true;
            if (translatedLinearLayout.getVisibility() == View.GONE) {
                translatorEditText.setText(translation.getOriginal());
                translateHeaderTextView.setText(translation.getTranslatePretty().getText());
                if (translation.getDictionaryPretty() != null &&
                        translation.getDictionaryPretty().getDef().length > 0)
                    translateDescrTextView.setText(translation.getDictionaryPretty().getDef()[0].getTs());
                translatedLinearLayout.startAnimation(animFadingInvert);
            } else translatedLinearLayout.startAnimation(animFadingForRepeat);
        }
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

    @Override
    public void onTextChanged(EditText view, CharSequence text) {
        if (!textInputLayout.isCounterEnabled() && text.length() > 15 && animIsNotRun) {
            buttonContainer.startAnimation(animButtonDown);
        }
        if (textInputLayout.isCounterEnabled() && text.length() <= 15 && animIsNotRun) {
            buttonContainer.startAnimation(animButtonUp);
        }
        if (text.length() == 0) {
            translationUpdated = false;
            translatedLinearLayout.startAnimation(animFading);
        } else if (translationUpdated && text.length() > 0) {
            translatedLinearLayout.startAnimation(animFadingInvert);
        }
        if (text.length() > MAX_LETTERS) {
            translateButton.setClickable(false);
            translateButton.setBackground(getResources().getDrawable(R.drawable.button_inactive));
        } else {
            translateButton.setClickable(true);
            translateButton.setBackground(getResources().getDrawable(R.drawable.button));
        }
    }

    @Override
    public void showMessage(String localizedMessage) {
        Toast.makeText(getActivity(), localizedMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showTranslationFavorite(boolean isFavorite) {
        favoriteImageView.setImageDrawable(getResources()
                .getDrawable(isFavorite ? R.drawable.star_full : R.drawable.star));
    }

    private void closeKeyboard() {
        ((InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(getView().getWindowToken(), 0);
    }

    private void hideTheHorror() {
        animButtonDown = AnimationUtils.loadAnimation(getActivity(), R.anim.button_down);
        animButtonUp = AnimationUtils.loadAnimation(getActivity(), R.anim.button_up);
        animFading = AnimationUtils.loadAnimation(getActivity(), R.anim.fading);
        animFadingForRepeat = AnimationUtils.loadAnimation(getActivity(), R.anim.fading);
        animFadingInvert = AnimationUtils.loadAnimation(getActivity(), R.anim.fading_invert);
        animNavHide = AnimationUtils.loadAnimation(getActivity(), R.anim.nav_up);
        animLeftToCenter = AnimationUtils.loadAnimation(getActivity(), R.anim.swap_left_to_center);
        animCenterToLeft = AnimationUtils.loadAnimation(getActivity(), R.anim.swap_center_to_left);
        animRightToCenter = AnimationUtils.loadAnimation(getActivity(), R.anim.swap_right_to_center);
        animCenterToRight = AnimationUtils.loadAnimation(getActivity(), R.anim.swap_center_to_right);

        animIsNotRun = true;
        navigationView.startAnimation(animNavHide);

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
        animFadingForRepeat.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                translateHeaderTextView.setText(translation.getTranslatePretty().getText());
                if (translation.getDictionaryPretty() != null &&
                        translation.getDictionaryPretty().getDef().length > 0) {
                    translateDescrTextView.setText(translation.getDictionaryPretty().getDef()[0].getTs());
                }
                translatedLinearLayout.startAnimation(animFadingInvert);
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
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        animCenterToLeft.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                translatorPresenter.saveDirState(dir);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        animFadingInvert.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                translatedLinearLayout.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        animFading.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                translatedLinearLayout.setVisibility(View.GONE);
                translatorPresenter.clearTranslatedText();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        animButtonUp.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                animIsNotRun = false;
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                textInputLayout.setCounterEnabled(false);
                animIsNotRun = true;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        animButtonDown.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                animIsNotRun = false;
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                textInputLayout.setCounterEnabled(true);
                animIsNotRun = true;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }
}

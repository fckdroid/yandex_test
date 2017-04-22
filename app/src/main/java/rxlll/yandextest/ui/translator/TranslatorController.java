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
import android.widget.TextView;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.bluelinelabs.conductor.RouterTransaction;
import com.bluelinelabs.conductor.changehandler.VerticalChangeHandler;

import rxlll.yandextest.R;
import rxlll.yandextest.ui.base.MoxyController;
import rxlll.yandextest.ui.langs.LangsController;

/**
 * Created by Maksim Sukhotski on 4/16/2017.
 */

public class TranslatorController extends MoxyController implements TranslatorView, LangsController.TargetLangEntryControllerListener {

    private static final boolean TYPE_L = false;
    private static final boolean TYPE_R = true;
    @InjectPresenter
    TranslatorPresenter translatorPresenter;
    private TextView leftTextView;
    private TextView rightTextView;
    private View navigationView;
    private View swapImageView;
    private TextView copyRightTextView;

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
        navigationView = getActivity().findViewById(R.id.navigation);

        leftTextView.setOnClickListener(v ->
                translatorPresenter.pushLangsController(TYPE_L, leftTextView.getText().toString()));
        rightTextView.setOnClickListener(v ->
                translatorPresenter.pushLangsController(TYPE_R, leftTextView.getText().toString()));

        swapImageView.setOnClickListener(v ->
                translatorPresenter.setDir(new Pair<>(rightTextView.getText(), leftTextView.getText())));
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
    public void showDir(Pair<String, String> dir) {
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
                leftTextView.setText(dir.first);
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
                rightTextView.setText(dir.second);
                rightTextView.startAnimation(animCenterToRight);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        leftTextView.startAnimation(animLeftToCenter);
        rightTextView.startAnimation(animRightToCenter);
    }

    @Override
    public void showLangsController(boolean type, String s) {
        ((InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getView().getWindowToken(), 0);
        getRouter().pushController(RouterTransaction.with(new LangsController(this, type, s))
                .pushChangeHandler(new VerticalChangeHandler())
                .popChangeHandler(new VerticalChangeHandler()));
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
    }


    @Override
    public void onLangPicked(boolean type, String lang) {
        if (type == TYPE_L) {
            leftTextView.setText(lang);
        } else {
            rightTextView.setText(lang);
        }
        translatorPresenter.setDir(new Pair(leftTextView.getText().toString(), rightTextView.getText().toString()));
    }
}

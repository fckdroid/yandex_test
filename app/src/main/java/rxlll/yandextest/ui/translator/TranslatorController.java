package rxlll.yandextest.ui.translator;

import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

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
        navigationView.setVisibility(View.VISIBLE);

        leftTextView.setOnClickListener(v -> translatorPresenter.pushLangsController());
        rightTextView.setOnClickListener(v -> Toast.makeText(getActivity(), "right" + rightTextView.getText(), Toast.LENGTH_SHORT).show());
        swapImageView.setOnClickListener(v -> translatorPresenter.setRoute(new Pair<>(rightTextView.getText(), leftTextView.getText())));
        copyRightTextView.setText(Html.fromHtml(getActivity().getString(R.string.translateFragment_copyright)));
        copyRightTextView.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override
    public void showRoute(Pair<String, String> route) {
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
                leftTextView.setText(route.first);
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
                rightTextView.setText(route.second);
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
    public void showLangsController() {
        getRouter().pushController(RouterTransaction.with(new LangsController(this))
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
    public void onLangPicked(String lang) {
        //// TODO: 4/22/2017  
    }
}

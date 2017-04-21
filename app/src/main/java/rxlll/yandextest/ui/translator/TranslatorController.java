package rxlll.yandextest.ui.translator;

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

public class TranslatorController extends MoxyController implements TranslatorView {

    @InjectPresenter
    TranslatorPresenter translatorPresenter;

    private TextView leftTextView;
    private TextView rightTextView;

    @Override
    protected View inflateView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.controller_translator, container, false);
    }

    @Override
    protected void onViewBound(View view) {
        leftTextView = (TextView) view.findViewById(R.id.lang_left_text_view);
        rightTextView = (TextView) view.findViewById(R.id.lang_right_text_view);
        leftTextView.setOnClickListener(v -> getRouter().pushController(RouterTransaction.with(new LangsController())
                .pushChangeHandler(new VerticalChangeHandler())
                .popChangeHandler(new VerticalChangeHandler())));
        rightTextView.setOnClickListener(v -> Toast.makeText(getActivity(), "right" + rightTextView.getText(), Toast.LENGTH_SHORT).show());
        view.findViewById(R.id.swap_image_view)
                .setOnClickListener(v -> translatorPresenter.setRoute(new Pair<>(rightTextView.getText(), leftTextView.getText())));
    }

    @Override
    public void showRoute(Pair<String, String> route) {
        getActivity().findViewById(R.id.swap_image_view)
                .startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.rotate));
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

}

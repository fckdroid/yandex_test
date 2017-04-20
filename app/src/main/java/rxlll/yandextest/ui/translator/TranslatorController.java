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

import rxlll.yandextest.R;
import rxlll.yandextest.ui.base.MoxyController;

/**
 * Created by Maksim Sukhotski on 4/16/2017.
 */

public class TranslatorController extends MoxyController implements TranslatorView {

    @InjectPresenter
    TranslatorPresenter translatorPresenter;

    TextView leftTextView;
    TextView rightTextView;

    @Override
    protected View inflateView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.controller_translator, container, false);
    }

    @Override
    protected void onViewBound(View view) {
        leftTextView = (TextView) view.findViewById(R.id.lang_left_text_view);
        rightTextView = (TextView) view.findViewById(R.id.lang_right_text_view);
        leftTextView.setOnClickListener(v -> Toast.makeText(getActivity(), "left" + leftTextView.getText(), Toast.LENGTH_SHORT).show());
        rightTextView.setOnClickListener(v -> Toast.makeText(getActivity(), "right" + rightTextView.getText(), Toast.LENGTH_SHORT).show());
        view.findViewById(R.id.swap_image_view)
                .setOnClickListener(v -> translatorPresenter.swapLangs());
    }

    @Override
    public void showCurrentRoute(Pair<String, String> route) {
        ((TextView) getActivity().findViewById(R.id.lang_left_text_view)).setText(route.first);
        ((TextView) getActivity().findViewById(R.id.lang_right_text_view)).setText(route.second);
    }

    @Override
    public void swapLangs() {
        String leftPreviousText = leftTextView.getText().toString();
        String rightPreviousText = rightTextView.getText().toString();
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
                leftTextView.setText(rightPreviousText);
                leftTextView.startAnimation(animCenterToLeft);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        leftTextView.startAnimation(animLeftToCenter);
        animRightToCenter.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                rightTextView.setText(leftPreviousText);
                rightTextView.startAnimation(animCenterToRight);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        rightTextView.startAnimation(animRightToCenter);

    }
}

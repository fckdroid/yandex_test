package rxlll.yandextest.ui.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpDelegate;
import com.bluelinelabs.conductor.Controller;
import com.bluelinelabs.conductor.ControllerChangeHandler;
import com.bluelinelabs.conductor.ControllerChangeType;

/**
 * Created by Maksim Sukhotski on 4/15/2017.
 */

public abstract class MoxyController extends Controller {

    private final MvpDelegate<MoxyController> mvpDelegate = new MvpDelegate<>(this);
    boolean isStateSaved = false;
    boolean hasExited = false;

    public MoxyController() {
        super();
        mvpDelegate.onCreate();
    }

    public MoxyController(@Nullable Bundle args) {
        super(args);
        mvpDelegate.onCreate(args);
    }

    protected abstract View inflateView(LayoutInflater inflater, ViewGroup container);

    protected void onViewBound(@NonNull View view) {
    }

    @Override
    protected void onAttach(@NonNull View view) {
        super.onAttach(view);
        mvpDelegate.onAttach();
    }

    @Override
    protected void onDetach(@NonNull View view) {
        super.onDetach(view);
        mvpDelegate.onDetach();
    }

    @Override
    protected void onDestroyView(@NonNull View view) {
        super.onDestroyView(view);
        mvpDelegate.onDetach();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        if (hasExited) App.refWatcher.watch(this);
        if (isStateSaved) return;
        mvpDelegate.onDestroy();
    }

    @NonNull
    @Override
    protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        View view = inflateView(inflater, container);
        onViewBound(view);
        return view;
    }

    @Override
    protected void onRestoreViewState(@NonNull View view, @NonNull Bundle savedViewState) {
        super.onRestoreViewState(view, savedViewState);
        mvpDelegate.onDestroyView();
        mvpDelegate.onCreate(savedViewState);
    }

    @Override
    protected void onSaveViewState(@NonNull View view, @NonNull Bundle outState) {
        super.onSaveViewState(view, outState);
        isStateSaved = true;
        mvpDelegate.onSaveInstanceState(outState);
    }

    @Override
    protected void onChangeEnded(@NonNull ControllerChangeHandler changeHandler, @NonNull ControllerChangeType changeType) {
        super.onChangeEnded(changeHandler, changeType);
        hasExited = !changeType.isEnter;
//        if (isDestroyed()) App.refWatcher.watch(this);
    }
}

package llltt.yandextest.ui.base;

import android.app.ActionBar;
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

import llltt.yandextest.App;

/** Created by Maksim Sukhotski on 4/15/2017. */

public abstract class MoxyController extends Controller {

    private final MvpDelegate<MoxyController> mvpDelegate = new MvpDelegate<>(this);
    boolean isStateSaved = false;
    boolean hasExited = false;

    public MoxyController() {
        this.mvpDelegate.onCreate();
    }

    public MoxyController(@Nullable Bundle args) {
        this.mvpDelegate.onCreate(args);
    }

    @NonNull
    @Override
    protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        View view = inflateView(inflater, container);
        onViewBound(view);
        return view;
    }


    protected abstract View inflateView(LayoutInflater inflater, ViewGroup container);

    protected abstract void onViewBound(View view);

    @Override
    protected void onAttach(@NonNull View view) {
        setTitle();
        super.onAttach(view);
    }

    protected void setTitle() {
        Controller parentController = getParentController();
        while (parentController != null) {
            if (parentController instanceof MoxyController &&
                    ((MoxyController) parentController).getTitle() != null) return;
            parentController = parentController.getParentController();
        }
        String title = getTitle();
        if (title != null) getActionBar().setTitle(title);
    }

    protected String getTitle() {
        return null;
    }

    private ActionBar getActionBar() {
        return ((ActionBarProvider) getActivity()).getSupportActionBar();
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
        if (isDestroyed()) App.refWatcher.watch(this);
    }
}

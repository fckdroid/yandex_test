package rxlll.yandextest.ui.pager;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.bluelinelabs.conductor.Router;
import com.bluelinelabs.conductor.RouterTransaction;
import com.bluelinelabs.conductor.support.RouterPagerAdapter;

import rxlll.yandextest.R;
import rxlll.yandextest.ui.base.MoxyController;
import rxlll.yandextest.ui.pager.favorites.FavoritesController;
import rxlll.yandextest.ui.pager.history.HistoryController;

/**
 * Created by Maksim Sukhotski on 4/17/2017.
 */

public class PagerController extends MoxyController implements PagerView {

    public static final String SELECTED_ITEM = "selected_item";
    private static final int COUNT_OF_PAGES = 2;
    private final RouterPagerAdapter pagerAdapter;
    private final ViewPager.OnPageChangeListener onPageChangedListener;
    @InjectPresenter
    PagerPresenter pagerPresenter;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private int posSelected;

    public PagerController() {
        pagerAdapter = new RouterPagerAdapter(this) {
            @Override
            public void configureRouter(@NonNull Router router, int position) {
                if (!router.hasRootController()) {
                    switch (position) {
                        case 0:
                            router.setRoot(RouterTransaction.with(new HistoryController()));
                            break;
                        case 1:
                            router.setRoot(RouterTransaction.with(new FavoritesController()));
                            break;
                    }
                }
            }

            @Override
            public int getCount() {
                return COUNT_OF_PAGES;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                if (position == 0) return HistoryController.TAB_NAME;
                return FavoritesController.TAB_NAME;
            }
        };
        onPageChangedListener = new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                posSelected = position;
                EditText searchEditText = (EditText) getActivity().findViewById(R.id.search_edit_text);
                if (searchEditText != null) {
                    if (position == 0) {
                        searchEditText.setHint("Найти в истории");
                    } else {
                        searchEditText.setHint("Найти в избранном");
                    }
                }
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        };
    }

    @Override
    protected void onViewBound(@NonNull View view) {
        super.onViewBound(view);
        viewPager = (ViewPager) view.findViewById(R.id.view_pager);
        tabLayout = (TabLayout) view.findViewById(R.id.tab_layout);
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(onPageChangedListener);
        tabLayout.setupWithViewPager(viewPager);


        view.findViewById(R.id.delete_image_view).setOnClickListener(v -> {
            new AlertDialog.Builder(getActivity())
                    .setTitle("Внимание!")
                    .setMessage("Вы действительно хотите очистить историю и избранное?")
                    .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                        pagerPresenter.deleteAll();
                    })
                    .setNegativeButton(android.R.string.no, (dialog, which) -> {
                        // do nothing
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        });
    }

    @Override
    protected void onRestoreViewState(@NonNull View view, @NonNull Bundle savedViewState) {
        EditText searchEditText = (EditText) view.findViewById(R.id.search_edit_text);
        if (posSelected == 0) searchEditText.setHint("Найти в истории");
        if (posSelected == 1) searchEditText.setHint("Найти в избранном");
        posSelected = savedViewState.getInt(SELECTED_ITEM);
        super.onRestoreViewState(view, savedViewState);
    }

    @Override
    protected void onSaveViewState(@NonNull View view, @NonNull Bundle outState) {
        outState.putInt(SELECTED_ITEM, posSelected);
        super.onSaveViewState(view, outState);
    }

    @Override
    protected void onDestroyView(@NonNull View view) {
        viewPager.setAdapter(null);
        super.onDestroyView(view);
    }

    @NonNull
    @Override
    protected View inflateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        return inflater.inflate(R.layout.controller_pager, container, false);
    }
}

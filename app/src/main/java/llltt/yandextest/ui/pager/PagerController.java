package llltt.yandextest.ui.pager;

import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bluelinelabs.conductor.Router;
import com.bluelinelabs.conductor.RouterTransaction;
import com.bluelinelabs.conductor.support.RouterPagerAdapter;

import llltt.yandextest.R;
import llltt.yandextest.ui.base.MoxyController;
import llltt.yandextest.ui.favorites.FavoritesController;
import llltt.yandextest.ui.history.HistoryController;

/**
 * Created by Maksim Sukhotski on 4/17/2017.
 */

public class PagerController extends MoxyController implements PagerView {
    public static final int COUNT_OF_PAGES = 2;
    private final RouterPagerAdapter pagerAdapter;
    ViewPager viewPager;
    TabLayout tabLayout;

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
    }

    @Override
    protected void onViewBound(@NonNull View view) {
        super.onViewBound(view);
        viewPager = (ViewPager) view.findViewById(R.id.view_pager);
        tabLayout = (TabLayout) view.findViewById(R.id.tab_layout);
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
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

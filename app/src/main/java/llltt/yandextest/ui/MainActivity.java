package llltt.yandextest.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.bluelinelabs.conductor.Conductor;
import com.bluelinelabs.conductor.Router;
import com.bluelinelabs.conductor.RouterTransaction;

import llltt.yandextest.R;
import llltt.yandextest.ui.base.ActionBarProvider;
import llltt.yandextest.ui.pager.PagerController;
import llltt.yandextest.ui.settings.SettingsController;
import llltt.yandextest.ui.translator.TranslatorController;

public final class MainActivity extends AppCompatActivity implements ActionBarProvider {

    public static final String NAVIGATION_KEY = "navigation_key";

    Animation controllerAnim;
    Animation navigationAnim;
    private Router translatorRouter;
    private Router pagerRouter;
    private Router settingsRouter;
    private BottomNavigationView bottomNavigationView;

    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            View translatorView = findViewById(R.id.translator_container);
            View pagerView = findViewById(R.id.pager_container);
            View settingsView = findViewById(R.id.settings_container);
            translatorView.setVisibility(View.GONE);
            pagerView.setVisibility(View.GONE);
            settingsView.setVisibility(View.GONE);
            switch (item.getItemId()) {
                case R.id.translator:
                    translatorView.setVisibility(View.VISIBLE);
                    if (bottomNavigationView.getSelectedItemId() != item.getItemId())
                        translatorView.startAnimation(controllerAnim);
                    break;
                case R.id.pager:
                    pagerView.setVisibility(View.VISIBLE);
                    if (bottomNavigationView.getSelectedItemId() != item.getItemId())
                        pagerView.startAnimation(controllerAnim);
                    break;
                case R.id.settings:
                    settingsView.setVisibility(View.VISIBLE);
                    if (bottomNavigationView.getSelectedItemId() != item.getItemId())
                        settingsView.startAnimation(controllerAnim);
                    break;
            }
            findViewById(item.getItemId()).startAnimation(navigationAnim);
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        translatorRouter = Conductor.attachRouter(this,
                (ViewGroup) findViewById(R.id.translator_container),
                savedInstanceState);
        pagerRouter = Conductor.attachRouter(this,
                (ViewGroup) findViewById(R.id.pager_container),
                savedInstanceState);
        settingsRouter = Conductor.attachRouter(this,
                (ViewGroup) findViewById(R.id.settings_container),
                savedInstanceState);
        translatorRouter.setRoot(RouterTransaction.with(new TranslatorController()));
        pagerRouter.setRoot(RouterTransaction.with(new PagerController()));
        settingsRouter.setRoot(RouterTransaction.with(new SettingsController()));

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);
        controllerAnim = AnimationUtils.loadAnimation(this, R.anim.controller);
        navigationAnim = AnimationUtils.loadAnimation(this, R.anim.navigation);
//        bottomNavigationView.getSelectedItemId()
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        bottomNavigationView.setSelectedItemId(savedInstanceState.getInt(NAVIGATION_KEY));
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(NAVIGATION_KEY, bottomNavigationView.getSelectedItemId());
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        Router visibleRouter = getVisibleRouter();
        if (!visibleRouter.handleBack()) {
            if (visibleRouter != translatorRouter) {
                bottomNavigationView.setSelectedItemId(R.id.translator);
            } else {
                super.onBackPressed();
            }
        }
    }

    private Router getVisibleRouter() {
        if (findViewById(R.id.pager_container).getVisibility() == View.VISIBLE)
            return pagerRouter;
        if (findViewById(R.id.settings_container).getVisibility() == View.VISIBLE)
            return settingsRouter;
        return translatorRouter;
    }
}

package rxlll.yandextest.ui;

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
import com.bluelinelabs.conductor.changehandler.FadeChangeHandler;

import rxlll.yandextest.R;
import rxlll.yandextest.data.repositories.database.Translation;
import rxlll.yandextest.ui.pager.PagerController;
import rxlll.yandextest.ui.settings.SettingsController;
import rxlll.yandextest.ui.translator.TranslatorController;

public final class MainActivity extends AppCompatActivity {

    public static final String NAVIGATION_KEY = "navigation_key";
    public static final String TRANSLATOR_CONTROLLER_TAG = "TranslatorController";
    private static final String VISIBLE_LAYOUT_ID = "visible_layout_id";

    Animation controllerAnim;
    Animation navigationAnim;
    private Router translatorRouter;
    private Router pagerRouter;
    private Router settingsRouter;
    private BottomNavigationView bottomNavigationView;
    private int visibleLayoutNumber = -1;
    private View translatorView;
    private View pagerView;
    private View settingsView;

    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        public boolean canUpdate;

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            translatorView.setVisibility(View.GONE);
            pagerView.setVisibility(View.GONE);
            settingsView.setVisibility(View.GONE);
            switch (item.getItemId()) {
                case R.id.translator:
                    translatorView.setVisibility(View.VISIBLE);
                    visibleLayoutNumber = 1;
                    if (bottomNavigationView.getSelectedItemId() != item.getItemId())
                        translatorView.startAnimation(controllerAnim);
                    break;
                case R.id.pager:
                    pagerView.setVisibility(View.VISIBLE);
                    visibleLayoutNumber = 2;
                    if (bottomNavigationView.getSelectedItemId() != item.getItemId())
                        pagerView.startAnimation(controllerAnim);
                    if (!pagerRouter.hasRootController() || canUpdate)
                        pagerRouter.setRoot(RouterTransaction.with(new PagerController())
                                .pushChangeHandler(new FadeChangeHandler())
                                .popChangeHandler(new FadeChangeHandler()));
                    canUpdate = true;
                    break;
                case R.id.settings:
                    settingsView.setVisibility(View.VISIBLE);
                    visibleLayoutNumber = 3;
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
        translatorView = findViewById(R.id.translator_container);
        pagerView = findViewById(R.id.pager_container);
        settingsView = findViewById(R.id.settings_container);
        translatorRouter = Conductor.attachRouter(this,
                (ViewGroup) findViewById(R.id.translator_container),
                savedInstanceState);
        pagerRouter = Conductor.attachRouter(this,
                (ViewGroup) findViewById(R.id.pager_container),
                savedInstanceState);
        settingsRouter = Conductor.attachRouter(this,
                (ViewGroup) findViewById(R.id.settings_container),
                savedInstanceState);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);
        controllerAnim = AnimationUtils.loadAnimation(this, R.anim.controller);
        navigationAnim = AnimationUtils.loadAnimation(this, R.anim.navigation);


    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!translatorRouter.hasRootController())
            translatorRouter.setRoot(RouterTransaction.with(new TranslatorController())
                    .tag(TRANSLATOR_CONTROLLER_TAG));
        if (!pagerRouter.hasRootController())
            pagerRouter.setRoot(RouterTransaction.with(new PagerController()));
        if (!settingsRouter.hasRootController())
            settingsRouter.setRoot(RouterTransaction.with(new SettingsController()));
        if (visibleLayoutNumber != -1) {
            translatorView.setVisibility(View.GONE);
            pagerView.setVisibility(View.GONE);
            settingsView.setVisibility(View.GONE);
            switch (visibleLayoutNumber) {
                case 1:
                    findViewById(R.id.translator_container).setVisibility(View.VISIBLE);
                    break;
                case 2:
                    findViewById(R.id.pager_container).setVisibility(View.VISIBLE);
                    break;
                case 3:
                    findViewById(R.id.settings_container).setVisibility(View.VISIBLE);
                    break;

            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(VISIBLE_LAYOUT_ID, visibleLayoutNumber);
        super.onSaveInstanceState(outState);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        visibleLayoutNumber = savedInstanceState.getInt(VISIBLE_LAYOUT_ID);
        super.onRestoreInstanceState(savedInstanceState);
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

    public void showTranslatorController(Translation translation) {
        if (translatorRouter.getControllerWithTag(TRANSLATOR_CONTROLLER_TAG) != null)
            ((TranslatorController) translatorRouter.getControllerWithTag(TRANSLATOR_CONTROLLER_TAG))
                    .translatorPresenter.getViewState().showTranslation(translation);
        onBackPressed();
    }

    public void updateBookmarksController() {
        pagerRouter.setRoot(RouterTransaction.with(new PagerController())
                .pushChangeHandler(new FadeChangeHandler())
                .popChangeHandler(new FadeChangeHandler()));
    }
}

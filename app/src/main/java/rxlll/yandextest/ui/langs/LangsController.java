package rxlll.yandextest.ui.langs;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.List;

import rxlll.yandextest.R;
import rxlll.yandextest.data.repositories.database.Lang;
import rxlll.yandextest.ui.base.MoxyController;
import rxlll.yandextest.ui.translator.TranslatorController;

import static rxlll.yandextest.ui.translator.TranslatorController.TYPE_L;

/**
 * Created by Maksim Sukhotski on 4/21/2017.
 */

public class LangsController extends MoxyController implements LangsView {

    static final int FAVORITE_LANG_COUNT = 3;
    static final int ALL_LANG = 0;
    @InjectPresenter
    LangsPresenter langsPresenter;
    private boolean type;
    private String currentLang;
    private RecyclerView recyclerView;
    private LangsRecyclerAdapter recyclerAdapter;
    private Switch switchView;
    private boolean switchState;

    public LangsController() {

    }

    public LangsController(TranslatorController translatorController, boolean type, String s) {
        super();
        setTargetController(translatorController);
        this.type = type;
        this.currentLang = s;
    }

    @Override
    public void popController() {
        getRouter().popController(this);
    }

    @Override
    public void showLangs(List<Lang> langs) {
        recyclerAdapter = new LangsRecyclerAdapter(langs, currentLang, type, switchState);
        recyclerAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerAdapter.setOnLangClickListener(lang -> langsPresenter.setLang(type, lang, getTargetController()));
        recyclerAdapter.setOnSwitchListener(checked -> langsPresenter.setAutoDetect(type, checked, getTargetController()));
    }

    @Override
    public void showTitleText(boolean type) {
        ((TextView) getView().findViewById(R.id.header_text_view)).setText(type ? "Язык перевода" : "Язык оригинала");
    }

    @Override
    public void showSwitch(boolean b) {
        this.switchState = b;
    }

    @Override
    protected View inflateView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.controller_langs, container, false);
    }

    @Override
    protected void onViewBound(@NonNull View view) {
        super.onViewBound(view);
        langsPresenter.setTitleText(type);
        recyclerView = (RecyclerView) view.findViewById(R.id.langs_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        view.findViewById(R.id.back_image_view).setOnClickListener(v -> langsPresenter.popController());
        getActivity().findViewById(R.id.navigation).setVisibility(View.GONE);
        switchView = (Switch) view.findViewById(R.id.switch_view);
        if (type == TYPE_L) langsPresenter.setSwitchState();
    }

    public interface TargetLangEntryControllerListener {
        void onLangPicked(boolean type, String lang);
    }
}

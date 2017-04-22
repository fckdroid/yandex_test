package rxlll.yandextest.ui.langs;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.List;

import rxlll.yandextest.R;
import rxlll.yandextest.data.repositories.database.Lang;
import rxlll.yandextest.ui.base.MoxyController;
import rxlll.yandextest.ui.translator.TranslatorController;

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
    private RecyclerView allRecyclerView;
    private RecyclerView frequentlyRecyclerView;
    private LangsRecyclerAdapter allRecyclerViewAdapter;
    private LangsRecyclerAdapter frequentlyRecyclerViewAdapter;

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
        frequentlyRecyclerViewAdapter = new LangsRecyclerAdapter(langs, currentLang, FAVORITE_LANG_COUNT);
        frequentlyRecyclerViewAdapter.notifyDataSetChanged();
        frequentlyRecyclerView.setAdapter(frequentlyRecyclerViewAdapter);
        frequentlyRecyclerView.setHasFixedSize(true);

        allRecyclerViewAdapter = new LangsRecyclerAdapter(langs, currentLang, ALL_LANG);
        allRecyclerViewAdapter.notifyDataSetChanged();
        allRecyclerView.setAdapter(allRecyclerViewAdapter);
        allRecyclerView.setHasFixedSize(true);

        frequentlyRecyclerViewAdapter.setOnItemClickListener(lang -> {
            langsPresenter.setLang(type, lang, getTargetController());
        });
        allRecyclerViewAdapter.setOnItemClickListener(lang -> {
            langsPresenter.setLang(type, lang, getTargetController());
        });
    }

    @Override
    public void showTitleText(boolean type) {
        ((TextView) getView().findViewById(R.id.header_text_view)).setText(type ? "Язык перевода" : "Язык оригинала");
    }

    @Override
    protected View inflateView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.controller_langs, container, false);
    }

    @Override
    protected void onViewBound(@NonNull View view) {
        super.onViewBound(view);
        langsPresenter.setTitleText(type);
        view.findViewById(R.id.switch1).setVisibility(type ? View.GONE : View.VISIBLE);
        allRecyclerView = (RecyclerView) view.findViewById(R.id.all_recycler_view);
        frequentlyRecyclerView = (RecyclerView) view.findViewById(R.id.frequently_recycler_view);

        allRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        allRecyclerView.setAdapter(allRecyclerViewAdapter);

        frequentlyRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        frequentlyRecyclerView.setAdapter(frequentlyRecyclerViewAdapter);

        view.findViewById(R.id.back_image_view).setOnClickListener(v -> {
            langsPresenter.popController();
        });
        getActivity().findViewById(R.id.navigation).setVisibility(View.GONE);
    }

    public interface TargetLangEntryControllerListener {
        void onLangPicked(boolean type, String lang);
    }
}

package rxlll.yandextest.ui.pager.history;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.List;

import rxlll.yandextest.R;
import rxlll.yandextest.data.repositories.database.Translation;
import rxlll.yandextest.ui.MainActivity;
import rxlll.yandextest.ui.base.MoxyController;
import rxlll.yandextest.ui.pager.PagerController;
import rxlll.yandextest.ui.pager.RecyclerAdapter;
import rxlll.yandextest.ui.pager.favorites.FavoritesController;

/**
 * Created by Maksim Sukhotski on 4/17/2017.
 */

public class HistoryController extends MoxyController implements HistoryView {

    public static final String TAB_NAME = "История";
    @InjectPresenter
    HistoryPresenter historyPresenter;
    private RecyclerView recyclerView;
    private RecyclerAdapter recyclerAdapter;
    private ViewGroup container;

    @Override
    protected View inflateView(LayoutInflater inflater, ViewGroup container) {
        this.container = container;
        return inflater.inflate(R.layout.part_recycler, container, false);
    }

    @Override
    protected void onViewBound(@NonNull View view) {
        super.onViewBound(view);
        recyclerView = (RecyclerView) view.findViewById(R.id.bookmarks_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void showTranslations(List<Translation> translations) {
        if (translations == null) return;
        recyclerAdapter = new RecyclerAdapter(translations);
        recyclerAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerAdapter.setOnTranslationClickListener(translation ->
                ((MainActivity) getActivity()).showTranslatorController(translation));
        recyclerAdapter.setOnFavoriteClickListener((translation, position) -> {
            recyclerAdapter.getTranslations().set(position, translation);
            recyclerAdapter.getTranslationsForFilter().set(position, translation);
            recyclerAdapter.notifyDataSetChanged();
            historyPresenter.setFavorite(translation);
            ((FavoritesController) ((PagerController) getParentController())
                    .getPagerAdapter().getRouter(1).getControllerWithTag(FavoritesController.TAB_NAME))
                    .updateTranslationsWith(translation);
        });
    }

    @Override
    public void updateTranslationsWith(Translation translation) {
        for (int i = 0; i < recyclerAdapter.getTranslations().size(); i++) {
            if (recyclerAdapter.getTranslations().get(i).getId() == translation.getId()) {
                recyclerAdapter.getTranslations().set(i, translation);
                recyclerAdapter.getTranslationsForFilter().set(i, translation);
                recyclerAdapter.notifyDataSetChanged();
                return;
            }
        }
    }

    @Override
    public void updateRecyclerWith(String s) {
        recyclerAdapter.filter(s);
    }
}

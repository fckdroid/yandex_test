package rxlll.yandextest.ui.history;

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

/**
 * Created by Maksim Sukhotski on 4/17/2017.
 */

public class HistoryController extends MoxyController implements HistoryView {

    public static final String TAB_NAME = "История";
    @InjectPresenter
    HistoryPresenter historyPresenter;
    private RecyclerView recyclerView;
    private BookmarksRecyclerAdapter recyclerAdapter;

    @Override
    protected View inflateView(LayoutInflater inflater, ViewGroup container) {
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
        recyclerAdapter = new BookmarksRecyclerAdapter(translations);
        recyclerAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerAdapter.setOnTranslationClickListener(translation ->
                ((MainActivity) getActivity()).showTranslatorController(translation));
        recyclerAdapter.setOnFavoriteClickListener((checked, position) -> {
            recyclerAdapter.getTranslations().set(position, checked);
            recyclerAdapter.notifyDataSetChanged();
            historyPresenter.setFavorite(checked);
        });
    }
}

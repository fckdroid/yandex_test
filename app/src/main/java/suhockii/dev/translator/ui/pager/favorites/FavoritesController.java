package suhockii.dev.translator.ui.pager.favorites;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.List;

import suhockii.dev.translator.R;
import suhockii.dev.translator.data.repositories.database.Translation;
import suhockii.dev.translator.ui.MainActivity;
import suhockii.dev.translator.ui.base.MoxyController;
import suhockii.dev.translator.ui.pager.PagerController;
import suhockii.dev.translator.ui.pager.RecyclerAdapter;
import suhockii.dev.translator.ui.pager.history.HistoryController;

/**
 * Created by Maksim Sukhotski on 4/17/2017.
 */

public class FavoritesController extends MoxyController implements FavoritesView {

    public static final String TAB_NAME = "Избранное";
    @InjectPresenter
    FavoritesPresenter favoritesPresenter;
    private RecyclerView recyclerView;
    private RecyclerAdapter recyclerAdapter;

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
        recyclerAdapter = new RecyclerAdapter(translations);
        recyclerAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerAdapter.setOnTranslationClickListener(translation ->
                ((MainActivity) getActivity()).showTranslatorController(translation));
        recyclerAdapter.setOnFavoriteClickListener((translation, position) -> {
            recyclerAdapter.getTranslations().remove(position);
            recyclerAdapter.getTranslationsForFilter().remove(position);
            recyclerAdapter.notifyItemRemoved(position);
            recyclerAdapter.notifyItemRangeChanged(position, recyclerAdapter.getItemCount());
            favoritesPresenter.updateTranslation(translation);
            ((HistoryController) ((PagerController) getParentController())
                    .getPagerAdapter().getRouter(0).getControllerWithTag(HistoryController.TAB_NAME))
                    .updateTranslationsWith(translation);
        });
    }

    @Override
    public void updateTranslationsWith(Translation translation) {
        if (translation.getIsFavorite()) {
            recyclerAdapter.getTranslations().add(translation);
            recyclerAdapter.getTranslationsForFilter().add(translation);
            recyclerAdapter.notifyDataSetChanged();
        } else {
            recyclerAdapter.getTranslations().remove(translation);
            recyclerAdapter.getTranslationsForFilter().remove(translation);
            recyclerAdapter.notifyDataSetChanged();

        }
    }

    @Override
    public void updateRecyclerWith(String s) {
        recyclerAdapter.filter(s);
    }
}

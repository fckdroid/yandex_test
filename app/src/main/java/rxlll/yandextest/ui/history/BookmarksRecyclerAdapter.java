package rxlll.yandextest.ui.history;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import rxlll.yandextest.R;
import rxlll.yandextest.data.repositories.database.Translation;

/**
 * Created by Maksim Sukhotski on 4/22/2017.
 */

class BookmarksRecyclerAdapter extends RecyclerView.Adapter<BookmarksRecyclerAdapter.RecyclerViewHolder> {
    private List<Translation> translations;
    private OnTranslationClickListener onTranslationClickListener;
    private OnFavoriteClickListener onFavoriteClickListener;

    public BookmarksRecyclerAdapter(List<Translation> translations) {
        this.translations = translations;
    }

    BookmarksRecyclerAdapter setOnTranslationClickListener(OnTranslationClickListener onTranslationClickListener) {
        this.onTranslationClickListener = onTranslationClickListener;
        return this;
    }

    BookmarksRecyclerAdapter setOnFavoriteClickListener(OnFavoriteClickListener onFavoriteClickListener) {
        this.onFavoriteClickListener = onFavoriteClickListener;
        return this;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecyclerViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_bookmark, null));
    }

    @Override
    public void onBindViewHolder(BookmarksRecyclerAdapter.RecyclerViewHolder holder, int position) {
        holder.originalTextView.setText(translations.get(position).getOriginal());
        holder.targetTextView.setText(translations.get(position).getTranslatePretty().getText());
        holder.dirTextView.setText(translations.get(position).getDir());
        holder.itemView.setOnClickListener(view -> onTranslationClickListener.onTranslationClick(translations.get(position)));
        holder.favoriteImageView.setOnClickListener(view -> {
            Translation translation = translations.get(position);
            translation.setIsFavorite(!translation.getIsFavorite());
            onFavoriteClickListener.onFavoriteClick(translation);
        });
    }

    @Override
    public int getItemCount() {
        return translations.size();
    }

    interface OnTranslationClickListener {
        void onTranslationClick(Translation translation);
    }

    interface OnFavoriteClickListener {
        void onFavoriteClick(Translation checked);
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder {
        ImageView favoriteImageView;
        TextView originalTextView;
        TextView targetTextView;
        TextView dirTextView;

        RecyclerViewHolder(View itemView) {
            super(itemView);
            favoriteImageView = (ImageView) itemView.findViewById(R.id.favorite_image_view);
            originalTextView = (TextView) itemView.findViewById(R.id.original_text_view);
            targetTextView = (TextView) itemView.findViewById(R.id.target_text_view);
            dirTextView = (TextView) itemView.findViewById(R.id.dir_text_view);
        }
    }
}

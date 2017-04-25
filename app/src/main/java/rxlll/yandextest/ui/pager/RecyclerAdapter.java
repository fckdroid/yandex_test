package rxlll.yandextest.ui.pager;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import rxlll.yandextest.R;
import rxlll.yandextest.data.repositories.database.Translation;

/**
 * Created by Maksim Sukhotski on 4/22/2017.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder> {
    private List<Translation> translations;
    private OnTranslationClickListener onTranslationClickListener;
    private OnFavoriteClickListener onFavoriteClickListener;

    public RecyclerAdapter(List<Translation> translations) {
        this.translations = translations;
    }

    public RecyclerAdapter setOnTranslationClickListener(OnTranslationClickListener onTranslationClickListener) {
        this.onTranslationClickListener = onTranslationClickListener;
        return this;
    }

    public RecyclerAdapter setOnFavoriteClickListener(OnFavoriteClickListener onFavoriteClickListener) {
        this.onFavoriteClickListener = onFavoriteClickListener;
        return this;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecyclerViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_bookmark, null));
    }

    @Override
    public void onBindViewHolder(RecyclerAdapter.RecyclerViewHolder holder, int position) {
        holder.originalTextView.setText(translations.get(position).getOriginal());
        holder.targetTextView.setText(translations.get(position).getTranslateObject().getText());
        holder.dirTextView.setText(translations.get(position).getDirection());
        holder.favoriteImageView.setImageResource(translations.get(position).getIsFavorite() ? R.drawable.star_full : R.drawable.star);
        holder.linearLayout.setOnClickListener(view -> onTranslationClickListener.onTranslationClick(translations.get(position)));
        holder.favoriteImageView.setOnClickListener(view -> {
            Translation translation = translations.get(position);
            translation.setIsFavorite(!translation.getIsFavorite());
            onFavoriteClickListener.onFavoriteClick(translation, position);
        });
    }

    public List<Translation> getTranslations() {
        return translations;
    }

    @Override
    public int getItemCount() {
        return translations.size();
    }

    public interface OnTranslationClickListener {
        void onTranslationClick(Translation translation);
    }

    public interface OnFavoriteClickListener {
        void onFavoriteClick(Translation checked, int position);
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder {
        ImageView favoriteImageView;
        TextView originalTextView;
        TextView targetTextView;
        TextView dirTextView;
        LinearLayout linearLayout;

        RecyclerViewHolder(View itemView) {
            super(itemView);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.holder_view);
            favoriteImageView = (ImageView) itemView.findViewById(R.id.favorite_image_view);
            originalTextView = (TextView) itemView.findViewById(R.id.original_text_view);
            targetTextView = (TextView) itemView.findViewById(R.id.target_text_view);
            dirTextView = (TextView) itemView.findViewById(R.id.dir_text_view);
        }
    }
}

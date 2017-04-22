package rxlll.yandextest.ui.langs;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import rxlll.yandextest.R;
import rxlll.yandextest.data.repositories.database.Lang;

import static rxlll.yandextest.ui.langs.LangsController.ALL_LANG;
import static rxlll.yandextest.ui.langs.LangsController.FAVORITE_LANG_COUNT;

/**
 * Created by Maksim Sukhotski on 4/22/2017.
 */

class LangsRecyclerAdapter extends RecyclerView.Adapter<LangsRecyclerAdapter.RecyclerViewHolder> {
    private final String checkedLang;
    private OnItemClickmapener onItemClickmapener;
    private List<Lang> list;

    public LangsRecyclerAdapter(List<Lang> list, String checkedLang, int favoritesCount) {
        if (favoritesCount == ALL_LANG) {
            this.list = new ArrayList<>();
            this.list.addAll(list);
            for (int i = 0; i < FAVORITE_LANG_COUNT; i++) {
                this.list.remove(i);
            }
        } else {
            for (int i = 0; i < FAVORITE_LANG_COUNT; i++) {
                this.list = new ArrayList<>();
                this.list.add(list.get(i));
            }
        }

        this.checkedLang = checkedLang;
    }

    LangsRecyclerAdapter setOnItemClickListener(LangsRecyclerAdapter.OnItemClickmapener onItemClickmapener) {
        this.onItemClickmapener = onItemClickmapener;
        return this;
    }

    @Override
    public LangsRecyclerAdapter.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new LangsRecyclerAdapter.RecyclerViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_lang, null));
    }

    @Override
    public void onBindViewHolder(LangsRecyclerAdapter.RecyclerViewHolder holder, int position) {
        holder.itemView.setOnClickListener(view -> onItemClickmapener.onItemClick(list.get(position)));
        holder.textView.setText(list.get(position).getDescription());
//        if (list.get(position).getDescription() == checkedLang) {
//            holder.checkedImageView.setVisibility(View.VISIBLE);
//        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    interface OnItemClickmapener {
        void onItemClick(Lang lang);
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView checkedImageView;

        RecyclerViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.text_view);
            checkedImageView = (ImageView) itemView.findViewById(R.id.image_view);
        }
    }
}

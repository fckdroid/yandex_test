package rxlll.yandextest.ui.langs;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import java.util.List;

import rxlll.yandextest.R;
import rxlll.yandextest.data.repositories.database.Lang;

import static rxlll.yandextest.App.LOG_TAG;
import static rxlll.yandextest.ui.translator.TranslatorController.TYPE_L;
import static rxlll.yandextest.ui.translator.TranslatorController.TYPE_R;

/**
 * Created by Maksim Sukhotski on 4/22/2017.
 */

class LangsRecyclerAdapter extends RecyclerView.Adapter<LangsRecyclerAdapter.RecyclerViewHolder> {
    private static final int VIEW_TYPE_SWITCH = 2;
    private static final int VIEW_TYPE_DESCRIPTION = 1;
    private static final int VIEW_TYPE_LANG = 0;
    private final String checkedLang;
    private final boolean type;
    private OnItemClickmapener onItemClickmapener;
    private List<Lang> langs;
    private boolean atFirst = true;

    public LangsRecyclerAdapter(List<Lang> langs, String checkedLang, boolean type) {
        this.langs = langs;
        this.checkedLang = checkedLang;
        this.type = type;
    }

    LangsRecyclerAdapter setOnItemClickListener(LangsRecyclerAdapter.OnItemClickmapener onItemClickmapener) {
        this.onItemClickmapener = onItemClickmapener;
        return this;
    }

    @Override
    public int getItemViewType(int position) {
        if (type == TYPE_L) {
            if (position > 5) return VIEW_TYPE_LANG;
            if (position == 5 || position == 1) return VIEW_TYPE_DESCRIPTION;
            if (position == 0) return VIEW_TYPE_SWITCH;
            return VIEW_TYPE_LANG;
        }
        if (type == TYPE_R) {
            if (position > 5) return VIEW_TYPE_LANG;
            if (position == 4 || position == 0) return VIEW_TYPE_DESCRIPTION;
            return VIEW_TYPE_LANG;
        }
        return VIEW_TYPE_LANG;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_LANG)
            return new RecyclerViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_lang, null));
        if (viewType == VIEW_TYPE_SWITCH)
            return new RecyclerViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_switch, null));
        return new RecyclerViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_lang_descr, null));
    }

    @Override
    public void onBindViewHolder(LangsRecyclerAdapter.RecyclerViewHolder holder, int position) {
        if (holder.getItemViewType() == VIEW_TYPE_LANG) {
            holder.itemView.setOnClickListener(view -> onItemClickmapener.onItemClick(langs.get(position)));
            holder.textView.setText(langs.get(position).getDescription());
        } else if (holder.getItemViewType() == VIEW_TYPE_SWITCH) {
            holder.switchAuto.setOnCheckedChangeListener((buttonView, isChecked) -> {
                Log.d(LOG_TAG, String.valueOf(isChecked));
            });
        } else {
            if (atFirst) {
                atFirst = false;
                holder.textView.setText("Часто используемые");
            } else {
                holder.textView.setText("Другие языки");
            }
        }
    }

    @Override
    public int getItemCount() {
        return langs.size();
    }

    interface OnItemClickmapener {
        void onItemClick(Lang lang);
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private final Switch switchAuto;
        TextView textView;
        ImageView checkedImageView;

        RecyclerViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.text_view);
            checkedImageView = (ImageView) itemView.findViewById(R.id.image_view);
            switchAuto = (Switch) itemView.findViewById(R.id.auto_switch);
        }
    }
}

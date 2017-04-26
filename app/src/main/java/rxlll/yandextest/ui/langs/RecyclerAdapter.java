package rxlll.yandextest.ui.langs;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import java.util.List;

import rxlll.yandextest.R;
import rxlll.yandextest.data.repositories.database.Lang;

import static rxlll.yandextest.ui.translator.TranslatorController.TYPE_L;
import static rxlll.yandextest.ui.translator.TranslatorController.TYPE_R;

/**
 * Created by Maksim Sukhotski on 4/22/2017.
 */

class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder> {
    private static final int VIEW_TYPE_SWITCH = 2;
    private static final int VIEW_TYPE_DESCRIPTION = 1;
    private static final int VIEW_TYPE_LANG = 0;
    private final String checkedLang;
    private final boolean type;
    private boolean switchState;
    private List<Lang> langs;
    private boolean atFirst;
    private OnLangClickClickListener onLangClickListener;
    private OnSwitchClickListener onSwitchClickListener;
    private Switch switchView;

    public RecyclerAdapter(List<Lang> langs, String checkedLang, boolean type, boolean switchState) {
        this.langs = langs;
        this.checkedLang = checkedLang;
        this.type = type;
        this.switchState = switchState;
        atFirst = true;
    }

    RecyclerAdapter setOnLangClickListener(OnLangClickClickListener onLangClickListener) {
        this.onLangClickListener = onLangClickListener;
        return this;
    }

    RecyclerAdapter setOnSwitchListener(OnSwitchClickListener onSwitchClickListener) {
        this.onSwitchClickListener = onSwitchClickListener;
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
    public void onBindViewHolder(RecyclerAdapter.RecyclerViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case VIEW_TYPE_LANG:
                holder.itemView.setOnClickListener(view -> onLangClickListener.onLangClick(langs.get(position - (type == TYPE_L ? 2 : 1))));
                holder.textView.setText(langs.get(position - (type == TYPE_L ? 2 : 1)).getDescription());
                holder.checkedImageView.setVisibility(View.INVISIBLE);
                if (checkedLang != null && checkedLang.equals(langs.get(position - (type == TYPE_L ? 2 : 1)).getDescription()))
                    holder.checkedImageView.setVisibility(View.VISIBLE);
                break;
            case VIEW_TYPE_SWITCH:
                holder.switchView.setOnCheckedChangeListener((buttonView, isChecked) ->
                        onSwitchClickListener.onSwitchClick(isChecked));
                switchView = holder.switchView;
                switchView.setChecked(switchState);
                break;
            case VIEW_TYPE_DESCRIPTION:
                if (atFirst) {
                    atFirst = false;
                    holder.textView.setText("Часто используемые");
                } else holder.textView.setText("Другие языки");
                break;
        }
    }

    @Override
    public int getItemCount() {
        return langs.size();
    }

    interface OnLangClickClickListener {
        void onLangClick(Lang lang);
    }

    interface OnSwitchClickListener {
        void onSwitchClick(boolean checked);
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private final Switch switchView;
        TextView textView;
        ImageView checkedImageView;

        RecyclerViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.text_view);
            checkedImageView = (ImageView) itemView.findViewById(R.id.image_view);
            switchView = (Switch) itemView.findViewById(R.id.switch_view);
        }
    }
}

package me.bakumon.moneykeeper.ui.setting;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import me.bakumon.moneykeeper.BR;
import me.bakumon.moneykeeper.R;


public class SettingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_HEADER = SettingListItem.TYPE_HEADER;
    private static final int TYPE_ITEM = SettingListItem.TYPE_ITEM;

    private List<SettingSectionEntity> mData;
    private Context mContext;

    // 点击事件监听器
    public interface OnItemClickListener {
        void onItemClick(View view, SettingSectionEntity item, int position);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    // 子控件点击事件监听器（如 Switch）
    public interface OnItemChildClickListener {
        void onItemChildClick(View view, SettingSectionEntity item, int position);
    }

    private OnItemChildClickListener onItemChildClickListener;

    public void setOnItemChildClickListener(OnItemChildClickListener listener) {
        this.onItemChildClickListener = listener;
    }

    public SettingAdapter(List<SettingSectionEntity> data) {
        this.mData = data != null ? data : new ArrayList<>();
    }

    public void setNewData(List<SettingSectionEntity> data) {
        this.mData = data != null ? data : new ArrayList<>();
        notifyDataSetChanged();
    }

    public List<SettingSectionEntity> getData() {
        return mData;
    }

    @Override
    public int getItemViewType(int position) {
        return mData.get(position).getItemType();
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        LayoutInflater inflater = LayoutInflater.from(mContext);
        if (viewType == TYPE_HEADER) {
            ViewDataBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_setting_head, parent, false);
            return new HeaderViewHolder(binding);
        } else {
            ViewDataBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_setting, parent, false);
            return new ItemViewHolder(binding);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        SettingSectionEntity item = mData.get(position);
        if (holder instanceof HeaderViewHolder) {
            ((HeaderViewHolder) holder).bind(item);
        } else if (holder instanceof ItemViewHolder) {
            ((ItemViewHolder) holder).bind(item);
        }
    }

    // 组头的 ViewHolder
    public class HeaderViewHolder extends RecyclerView.ViewHolder {
        private final ViewDataBinding binding;

        public HeaderViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(SettingSectionEntity item) {
            binding.setVariable(BR.header, item.getHeader());
            binding.executePendingBindings();
        }
    }

    // 设置项的 ViewHolder
    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private final ViewDataBinding binding;

        public ItemViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(SettingSectionEntity item) {
            SettingItem settingItem = item.getSettingItem();
            binding.setVariable(BR.settingItem, settingItem);
            binding.executePendingBindings();

            // 设置点击事件
            binding.getRoot().setOnClickListener(v -> {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(v, item, getAdapterPosition());
                }
            });

            // 处理子控件（如 Switch）的点击事件
            View switchView = binding.getRoot().findViewById(R.id.switch_item);
            if (switchView != null) {
                switchView.setOnClickListener(v -> {
                    if (onItemChildClickListener != null) {
                        onItemChildClickListener.onItemChildClick(v, item, getAdapterPosition());
                    }
                });
            }
        }
    }
}

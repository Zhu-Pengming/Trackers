package me.bakumon.moneykeeper.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseDataBindingAdapter<T> extends RecyclerView.Adapter<BaseDataBindingAdapter.DataBindingViewHolder> {
    protected List<T> mData;
    private int mLayoutResId;
    protected Context mContext;

    public BaseDataBindingAdapter(int layoutResId, @Nullable List<T> data) {
        this.mLayoutResId = layoutResId;
        this.mData = data != null ? data : new ArrayList<T>();
    }

    @Override
    public DataBindingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        LayoutInflater inflater = LayoutInflater.from(mContext);
        ViewDataBinding binding = DataBindingUtil.inflate(inflater, mLayoutResId, parent, false);
        return new DataBindingViewHolder(binding);
    }



    @Override
    public int getItemCount() {
        return mData.size();
    }

    public abstract void onBindItem(ViewDataBinding binding, T item, int position);

    public T getItem(int position) {
        return mData.get(position);
    }

    public List<T> getData() {
        return mData;
    }

    public void setNewData(@Nullable List<T> data) {
        if (data != null) {
            mData = data;
        } else {
            mData = new ArrayList<>();
        }
        notifyDataSetChanged();
    }

    public static class DataBindingViewHolder extends RecyclerView.ViewHolder {
        private final ViewDataBinding binding;

        public DataBindingViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public ViewDataBinding getBinding() {
            return binding;
        }
    }

    public interface OnItemClickListener<T> {
        void onItemClick(View view, T item, int position);
    }

    public interface OnItemLongClickListener<T> {
        boolean onItemLongClick(View view, T item, int position);
    }



    private OnItemClickListener<T> onItemClickListener;

    // 提供设置点击事件监听器的方法
    public void setOnItemClickListener(OnItemClickListener<T> listener) {
        this.onItemClickListener = listener;
    }



    private OnItemLongClickListener<T> onItemLongClickListener;

    public void setOnItemLongClickListener(OnItemLongClickListener<T> listener) {
        this.onItemLongClickListener = listener;
    }

    @Override
    public void onBindViewHolder(DataBindingViewHolder holder, int position) {
        T item = mData.get(position);
        onBindItem(holder.getBinding(), item, position);

        holder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(v, item, position);
            }
        });

        holder.itemView.setOnLongClickListener(v -> {
            if (onItemLongClickListener != null) {
                return onItemLongClickListener.onItemLongClick(v, item, position);
            }
            return false;
        });

        holder.getBinding().executePendingBindings();
    }

}
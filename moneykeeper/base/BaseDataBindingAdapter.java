package me.bakumon.moneykeeper.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public abstract class BaseDataBindingAdapter<T extends ItemViewModel> extends RecyclerView.Adapter<BaseDataBindingAdapter.DataBindingViewHolder> {

    private final LayoutInflater mInflater;
    private final List<T> mData;

    public BaseDataBindingAdapter(@LayoutRes int layoutResId, @Nullable List<T> data, Context context) {
        mInflater = LayoutInflater.from(context);
        mData = data;
    }

    @Override
    public DataBindingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewDataBinding binding = DataBindingUtil.inflate(mInflater, viewType, parent, false);
        return new DataBindingViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(DataBindingViewHolder holder, int position) {
        holder.bind(mData.get(position));
    }

    @Override
    public int getItemViewType(int position) {
        return mData.get(position).getLayoutResId();
    }

    public abstract T getItem(int position);

    public class DataBindingViewHolder extends RecyclerView.ViewHolder {
        private final ViewDataBinding binding;

        public DataBindingViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(T item) {
            binding.setVariable(item.getVariableId(), item);
            binding.executePendingBindings();
        }

        public ViewDataBinding getBinding() {
            return binding;
        }
    }
}
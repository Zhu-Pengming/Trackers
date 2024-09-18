package me.bakumon.moneykeeper.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import java.util.Collections;
import java.util.List;

public abstract class BaseDraggableAdapter<T> extends RecyclerView.Adapter<BaseDraggableAdapter.BaseViewHolder> {

    protected final List<T> mData;
    protected final LayoutInflater mInflater;
    protected final ItemTouchHelper mItemTouchHelper;

    public BaseDraggableAdapter(@LayoutRes int layoutResId, List<T> data, Context context) {
        mData = data;
        mInflater = LayoutInflater.from(context);
        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(this);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(null); // 确保在构造函数中绑定
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(viewType, parent, false);
        return new BaseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        T item = mData.get(position);
        holder.bind(item);
        onItemBind(holder, position);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mData.get(position).getLayoutResId();
    }

    public abstract static class BaseViewHolder extends RecyclerView.ViewHolder {
        public BaseViewHolder(View itemView) {
            super(itemView);
        }

        public abstract void bind(T item);
    }

    public interface ItemTouchHelperAdapter {
        boolean onItemMove(int fromPosition, int toPosition);
        void onItemDismiss(int position);
    }

    protected class SimpleItemTouchHelperCallback extends ItemTouchHelper.Callback {
        private final ItemTouchHelperAdapter adapter;

        public SimpleItemTouchHelperCallback(ItemTouchHelperAdapter adapter) {
            this.adapter = adapter;
        }

        @Override
        public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
            int swipeFlags = 0;
            return makeMovementFlags(dragFlags, swipeFlags);
        }

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder source, RecyclerView.ViewHolder target) {
            return adapter.onItemMove(source.getAdapterPosition(), target.getAdapterPosition());
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeFlags) {
            adapter.onItemDismiss(viewHolder.getAdapterPosition());
        }
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Collections.swap(mData, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        onItemDragMove(fromPosition, toPosition);
        return true;
    }

    @Override
    public void onItemDismiss(int position) {
        mData.remove(position);
        notifyItemRemoved(position);
        onItemDismissed(position);
    }

    protected void onItemBind(BaseViewHolder holder, int position) {
        // 可以在子类中实现
    }

    protected void onItemDragMove(int fromPosition, int toPosition) {
        // 可以在子类中实现
    }

    protected void onItemDismissed(int position) {
        // 可以在子类中实现
    }
}
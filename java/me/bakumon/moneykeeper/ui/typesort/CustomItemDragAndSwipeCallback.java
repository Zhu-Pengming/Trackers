package me.bakumon.moneykeeper.ui.typesort;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

public class CustomItemDragAndSwipeCallback extends ItemTouchHelper.Callback {

    private final TypeSortAdapter mAdapter;

    public CustomItemDragAndSwipeCallback(TypeSortAdapter adapter) {
        mAdapter = adapter;
    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        // 设置拖拽和滑动的方向
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        int swipeFlags = 0; // 如果你不需要滑动，可以将滑动方向设为0
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        // 在拖拽过程中更新数据的位置
        int fromPosition = viewHolder.getAdapterPosition();
        int toPosition = target.getAdapterPosition();
        mAdapter.moveItem(fromPosition, toPosition);
        return true;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        // 处理滑动删除（如果启用了滑动）
        int position = viewHolder.getAdapterPosition();
        mAdapter.removeItem(position);
    }

    @Override
    public boolean isLongPressDragEnabled() {
        // 是否允许长按拖拽
        return true;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        // 是否允许滑动删除
        return false;
    }
}


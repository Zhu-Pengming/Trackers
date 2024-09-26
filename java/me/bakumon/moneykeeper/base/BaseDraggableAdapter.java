package me.bakumon.moneykeeper.base;


import android.view.MotionEvent;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import java.util.Collections;
import java.util.List;

public abstract class BaseDraggableAdapter<T> extends BaseDataBindingAdapter<T> {
    private ItemTouchHelper itemTouchHelper;

    public BaseDraggableAdapter(int layoutResId, @Nullable List<T> data) {
        super(layoutResId, data);
    }

    // 设置 ItemTouchHelper
    public void setItemTouchHelper(ItemTouchHelper itemTouchHelper) {
        this.itemTouchHelper = itemTouchHelper;
    }

    // 实现拖拽逻辑
    @Override
    public void onBindViewHolder(final DataBindingViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);

        holder.itemView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getActionMasked() == MotionEvent.ACTION_DOWN && itemTouchHelper != null) {
                    itemTouchHelper.startDrag(holder);
                }
                return false;
            }
        });
    }

    // 实现 ItemTouchHelper.Callback
    public ItemTouchHelper.Callback getItemTouchHelperCallback() {
        return new ItemTouchHelper.Callback() {

            @Override
            public boolean isLongPressDragEnabled() {
                return true; // 是否启用长按拖拽
            }

            @Override
            public boolean isItemViewSwipeEnabled() {
                return false; // 是否启用滑动删除
            }

            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                return makeMovementFlags(dragFlags, 0);
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                int fromPosition = viewHolder.getAdapterPosition();
                int toPosition = target.getAdapterPosition();
                if (fromPosition < toPosition) {
                    for (int i = fromPosition; i < toPosition; i++) {
                        Collections.swap(mData, i, i + 1);
                    }
                } else {
                    for (int i = fromPosition; i > toPosition; i--) {
                        Collections.swap(mData, i, i - 1);
                    }
                }
                notifyItemMoved(fromPosition, toPosition);
                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                // 不处理滑动删除
            }
        };
    }
}
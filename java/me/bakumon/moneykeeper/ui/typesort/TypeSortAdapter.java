package me.bakumon.moneykeeper.ui.typesort;

import androidx.annotation.Nullable;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.List;

import me.bakumon.moneykeeper.BR;
import me.bakumon.moneykeeper.R;
import me.bakumon.moneykeeper.base.BaseDataBindingAdapter;
import me.bakumon.moneykeeper.base.BaseDraggableAdapter;
import me.bakumon.moneykeeper.database.entity.RecordType;

public class TypeSortAdapter extends BaseDraggableAdapter<RecordType> {

    private ItemTouchHelper mItemTouchHelper;

    public TypeSortAdapter(@Nullable List<RecordType> data) {
        super(R.layout.item_type_sort, data);
    }

    @Override
    public void onBindItem(ViewDataBinding binding, RecordType item, int position) {
        binding.setVariable(BR.recordType, item);
        binding.executePendingBindings();
    }

    @Override
    public ItemTouchHelper.Callback getItemTouchHelperCallback() {
        return new ItemTouchHelper.Callback() {

            @Override
            public boolean isLongPressDragEnabled() {
                return true; // 启用长按拖拽
            }

            @Override
            public boolean isItemViewSwipeEnabled() {
                return false; // 不启用滑动删除
            }

            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                return makeMovementFlags(dragFlags, 0);
            }

            @Override
            public boolean onMove(RecyclerView recyclerView,
                                  RecyclerView.ViewHolder viewHolder,
                                  RecyclerView.ViewHolder target) {
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

            @Override
            public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
                super.onSelectedChanged(viewHolder, actionState);
                if (actionState == ItemTouchHelper.ACTION_STATE_DRAG && viewHolder != null) {
                    // 拖拽开始，设置透明度为 0.8f
                    viewHolder.itemView.setAlpha(0.8f);
                }
            }

            @Override
            public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                super.clearView(recyclerView, viewHolder);
                // 拖拽结束，恢复透明度为 1f
                viewHolder.itemView.setAlpha(1f);
            }
        };
    }

    // 移动数据项的方法
    public void moveItem(int fromPosition, int toPosition) {
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
    }

    // 删除数据项的方法
    public void removeItem(int position) {
        if (mData != null && position >= 0 && position < mData.size()) {
            mData.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void enableDragItem(ItemTouchHelper itemTouchHelper) {
        mItemTouchHelper = itemTouchHelper;
    }


}

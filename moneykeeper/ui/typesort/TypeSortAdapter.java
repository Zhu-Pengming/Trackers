package me.bakumon.moneykeeper.ui.typesort;

import androidx.annotation.Nullable;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.List;

import me.bakumon.moneykeeper.BR;
import me.bakumon.moneykeeper.R;
import me.bakumon.moneykeeper.base.BaseDataBindingAdapter;
import me.bakumon.moneykeeper.base.BaseDraggableAdapter;
import me.bakumon.moneykeeper.database.entity.RecordType;

public class TypeSortAdapter extends BaseDraggableAdapter<RecordType> {

    public TypeSortAdapter(@Nullable List<RecordType> data) {
        super(R.layout.item_type_sort, data, null); // 假设这里传入了正确的 Context
    }

    @Override
    protected void convert(BaseDataBindingAdapter.DataBindingViewHolder helper, RecordType item) {
        ViewDataBinding binding = helper.getBinding();
        binding.setVariable(BR.recordType, item);
        binding.executePendingBindings();
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
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
        onItemDragMove(fromPosition, toPosition);
        return true;
    }

    @Override
    public void onItemDismiss(int position) {
        mData.remove(position);
        notifyItemRemoved(position);
        onItemDismissed(position);
    }

    // 以下方法用于处理拖拽时的UI变化
    public void onItemDragStart(RecyclerView.ViewHolder viewHolder, int pos) {
        viewHolder.itemView.setAlpha(0.8f);
    }

    public void onItemDragMoving(RecyclerView.ViewHolder source, int from, RecyclerView.ViewHolder target, int to) {
        // 可以在这里处理拖动时的逻辑，例如更新数据源
    }

    public void onItemDragEnd(RecyclerView.ViewHolder viewHolder, int pos) {
        viewHolder.itemView.setAlpha(1f);
    }
}
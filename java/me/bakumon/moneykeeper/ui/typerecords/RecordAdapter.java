package me.bakumon.moneykeeper.ui.typerecords;

import android.view.View;
import androidx.annotation.Nullable;
import androidx.databinding.ViewDataBinding;
import java.util.List;
import me.bakumon.moneykeeper.BR;
import me.bakumon.moneykeeper.R;
import me.bakumon.moneykeeper.base.BaseDataBindingAdapter;
import me.bakumon.moneykeeper.database.entity.RecordWithType;

public class RecordAdapter extends BaseDataBindingAdapter<RecordWithType> {

    // 定义长按点击事件监听器接口
    public interface OnItemLongClickListener {
        boolean onItemLongClick(View view, RecordWithType item, int position);
    }

    public interface OnItemChildLongClickListener {
        boolean onItemChildLongClick(View view, RecordWithType item, int position);
    }

    private OnItemLongClickListener onItemLongClickListener;
    private OnItemChildLongClickListener onItemChildLongClickListener;

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        this.onItemLongClickListener = listener;
    }

    public void setOnItemChildLongClickListener(OnItemChildLongClickListener listener) {
        this.onItemChildLongClickListener = listener;
    }

    public RecordAdapter(@Nullable List<RecordWithType> data) {
        super(R.layout.item_record_sort_money, data);
    }

    @Override
    public void onBindItem(ViewDataBinding binding, RecordWithType item, int position) {
        // 数据绑定
        binding.setVariable(BR.recordWithType, item);
        binding.executePendingBindings();

        // 设置根视图的长按点击事件
        binding.getRoot().setOnLongClickListener(v -> {
            if (onItemLongClickListener != null) {
                return onItemLongClickListener.onItemLongClick(v, item, position);
            }
            return false;
        });


    }

    public void setEmptyView(@Nullable View emptyView) {
        // 如果有数据，则不显示空视图
        if (getData() != null && !getData().isEmpty()) {
            emptyView.setVisibility(View.GONE);
        } else {
            emptyView.setVisibility(View.VISIBLE);
        }
        // 通常你会将空视图添加到 RecyclerView 的根布局中，这里只是设置视图可见性
    }
}
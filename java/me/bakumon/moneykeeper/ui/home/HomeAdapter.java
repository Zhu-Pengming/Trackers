/*
 * Copyright 2018 Bakumon. https://github.com/Bakumon
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package me.bakumon.moneykeeper.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import java.util.List;

import me.bakumon.moneykeeper.BR;
import me.bakumon.moneykeeper.R;
import me.bakumon.moneykeeper.base.BaseDataBindingAdapter;
import me.bakumon.moneykeeper.database.entity.RecordWithType;
import me.bakumon.moneykeeper.ui.typerecords.RecordAdapter;
import me.bakumon.moneykeeper.utill.DateUtils;

public class HomeAdapter extends BaseDataBindingAdapter<RecordWithType> {

    // 定义长按点击事件监听器接口
    public interface OnItemLongClickListener {
        boolean onItemLongClick(View view, RecordWithType item, int position);
    }

    public interface OnItemChildLongClickListener {
        boolean onItemChildLongClick(View view, RecordWithType item, int position);
    }

    private RecordAdapter.OnItemLongClickListener onItemLongClickListener;
    private RecordAdapter.OnItemChildLongClickListener onItemChildLongClickListener;

    public void setOnItemLongClickListener(RecordAdapter.OnItemLongClickListener listener) {
        this.onItemLongClickListener = listener;
    }

    public void setOnItemChildLongClickListener(RecordAdapter.OnItemChildLongClickListener listener) {
        this.onItemChildLongClickListener = listener;
    }

    public HomeAdapter(@Nullable List<RecordWithType> data) {
        super(R.layout.item_home, data);
    }

    @Override
    public void onBindItem(ViewDataBinding binding, RecordWithType item, int position) {
        // 设置长按点击事件
        binding.getRoot().setOnLongClickListener(v -> {
            if (onItemLongClickListener != null) {
                return onItemLongClickListener.onItemLongClick(v, item, position);
            }
            return false;
        });



        binding.setVariable(BR.recordWithType, item);

        boolean isDataShow = position == 0 ||
                !DateUtils.isSameDay(item.time, getData().get(position - 1).time);
        binding.setVariable(BR.isDataShow, isDataShow);

        binding.executePendingBindings();
    }

    @Nullable
    private View footerView;

    public void setFooterView(@Nullable View footerView) {
        this.footerView = footerView;
        notifyItemChanged(getData().size()); // 通知数据集变更以显示/隐藏 footer
    }

    public void removeAllFooterView() {
        this.footerView = null;
        notifyItemChanged(getData().size()); // 通知数据集变更以显示/隐藏 footer
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
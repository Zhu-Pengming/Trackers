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

package me.bakumon.moneykeeper.ui.statistics.reports;

import android.view.View;

import androidx.annotation.Nullable;
import androidx.databinding.ViewDataBinding;

import java.util.List;

import me.bakumon.moneykeeper.BR;
import me.bakumon.moneykeeper.R;
import me.bakumon.moneykeeper.base.BaseDataBindingAdapter;
import me.bakumon.moneykeeper.database.entity.TypeSumMoneyBean;

/**
 * ReportAdapter
 *
 * @author bakumon https://bakumon.me
 * @date 2018/5/25
 */

public class ReportAdapter extends BaseDataBindingAdapter<TypeSumMoneyBean> {

    public ReportAdapter(@Nullable List<TypeSumMoneyBean> data) {
        super(R.layout.item_report, data);
    }

    @Override
    public void onBindItem(ViewDataBinding binding, TypeSumMoneyBean item, int position) {
        // 设置数据绑定
        binding.setVariable(BR.typeSumMoney, item);
        binding.executePendingBindings();

        // 可以在这里添加其他与视图相关的逻辑，例如显示或隐藏某些视图元素
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

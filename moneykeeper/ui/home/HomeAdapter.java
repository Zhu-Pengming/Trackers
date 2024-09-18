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
import me.bakumon.moneykeeper.utill.DateUtils;

public class HomeAdapter extends BaseDataBindingAdapter<RecordWithType> {

    public HomeAdapter(@Nullable List<RecordWithType> data) {
        super(R.layout.item_home, data);
    }

    @Override
    protected void convert(DataBindingViewHolder helper, RecordWithType item) {
        ViewDataBinding binding = helper.getBinding();
        binding.getRoot().setOnLongClickListener(v -> {
            // 你的长按点击逻辑
            return true;
        });
        binding.setVariable(BR.recordWithType, item);
        boolean isDataShow = helper.getAdapterPosition() == 0 ||
                !DateUtils.isSameDay(item.time, getData().get(helper.getAdapterPosition() - 1).time);
        binding.setVariable(BR.isDataShow, isDataShow);
        binding.executePendingBindings();
    }

    @Override
    protected DataBindingViewHolder onCreateDataBindingViewHolder(ViewGroup parent, int layoutResId) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ViewDataBinding binding = DataBindingUtil.inflate(inflater, layoutResId, parent, false);
        return new DataBindingViewHolder(binding);
    }



}
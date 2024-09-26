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

package me.bakumon.moneykeeper.ui.typemanage;

import androidx.annotation.Nullable;
import androidx.databinding.ViewDataBinding;

import java.util.ArrayList;
import java.util.List;

import me.bakumon.moneykeeper.BR;
import me.bakumon.moneykeeper.R;
import me.bakumon.moneykeeper.base.BaseDataBindingAdapter;
import me.bakumon.moneykeeper.database.entity.RecordType;

/**
 * 类型管理适配器
 *
 * @author bakumon https://bakumon.me
 * @date 2018/5/4
 */

public class TypeManageAdapter extends BaseDataBindingAdapter<RecordType> {

    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;



    public void setOnItemClickListener(OnItemClickListener<RecordType> listener) {
        super.setOnItemClickListener(listener);
    }

    public void setOnItemLongClickListener(OnItemLongClickListener<RecordType> listener) {
        super.setOnItemLongClickListener(listener);
    }

    public TypeManageAdapter(@Nullable List<RecordType> data) {
        super(R.layout.item_type_manage, data);
    }
    // 记录当前类型
    private int mCurrentType;




    @Override
    public void onBindItem(ViewDataBinding binding, RecordType item, int position) {
        binding.setVariable(BR.recordType, item);
        binding.executePendingBindings();

        // Set click listener
        binding.getRoot().setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(v, item, position);
            }
        });

        // Set long-click listener
        binding.getRoot().setOnLongClickListener(v -> {
            if (onItemLongClickListener != null) {
                return onItemLongClickListener.onItemLongClick(v, item, position);
            }
            return false;
        });
    }

    // Renamed method to avoid conflict with base class
    public void setDataByType(@Nullable List<RecordType> data, int type) {
        mCurrentType = type;
        if (data != null && data.size() > 0) {
            List<RecordType> result = new ArrayList<>();
            for (RecordType recordType : data) {
                if (recordType.type == type) {
                    result.add(recordType);
                }
            }
            setNewData(result); // Calls the base class method
        } else {
            setNewData(null);
        }
    }

    public int getCurrentType() {
        return mCurrentType;
    }

}


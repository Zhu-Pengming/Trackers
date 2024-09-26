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

package me.bakumon.moneykeeper.ui.addtype;


import androidx.annotation.Nullable;
import androidx.databinding.ViewDataBinding;

import java.util.List;
import me.bakumon.moneykeeper.BR;
import me.bakumon.moneykeeper.R;
import me.bakumon.moneykeeper.base.BaseDataBindingAdapter;


public class TypeImgAdapter extends BaseDataBindingAdapter<TypeImgBean> {

    private int mCurrentCheckPosition;

    public TypeImgAdapter(@Nullable List<TypeImgBean> data) {
        super(R.layout.item_type_img, data);
    }

    @Override
    public void onBindItem(ViewDataBinding binding, TypeImgBean item, int position) {
        binding.setVariable(BR.typeImg, item);
        binding.executePendingBindings();
    }

    public void checkItem(int position) {
        // 选中某一个 item
        for (int i = 0; i < getData().size(); i++) {
            TypeImgBean temp = getData().get(i);
            if (temp != null) {
                temp.setChecked(i == position);
            }
        }
        mCurrentCheckPosition = position;
        notifyDataSetChanged();
    }

    /**
     * 获取当前选中的 item
     */
    public TypeImgBean getCurrentItem() {
        if (mCurrentCheckPosition >= 0 && mCurrentCheckPosition < getData().size()) {
            return getItem(mCurrentCheckPosition);
        }
        return null;
    }
}

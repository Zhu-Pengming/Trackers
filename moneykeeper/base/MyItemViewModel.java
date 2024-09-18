package me.bakumon.moneykeeper.base;

import me.bakumon.moneykeeper.datasource.AppDataSource;

public class MyItemViewModel extends BaseViewModel implements ItemViewModel {
    private int layoutResId;
    private int variableId;

    public MyItemViewModel(AppDataSource dataSource, int layoutResId, int variableId) {
        super(dataSource);
        this.layoutResId = layoutResId;
        this.variableId = variableId;
    }

    @Override
    public int getLayoutResId() {
        return layoutResId;
    }

    @Override
    public int getVariableId() {
        return variableId;
    }

    // 可以添加更多与数据相关的业务逻辑
}

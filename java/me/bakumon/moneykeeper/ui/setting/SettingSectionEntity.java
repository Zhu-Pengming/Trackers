package me.bakumon.moneykeeper.ui.setting;

import java.util.List;

public class SettingSectionEntity implements SettingListItem {
    private boolean isHeader;
    private String header; // 组头标题
    private SettingItem t; // 设置项

    public SettingSectionEntity(boolean isHeader, String header) {
        this.isHeader = isHeader;
        this.header = header;
    }

    public SettingSectionEntity(SettingItem t) {
        this.isHeader = false;
        this.t = t;
    }

    public boolean isHeader() {
        return isHeader;
    }

    public String getHeader() {
        return header;
    }

    public SettingItem getSettingItem() {
        return t;
    }

    @Override
    public int getItemType() {
        return isHeader ? TYPE_HEADER : TYPE_ITEM;
    }
}

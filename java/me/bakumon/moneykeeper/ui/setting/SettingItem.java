package me.bakumon.moneykeeper.ui.setting;


public class SettingItem {
    private String title;
    private String content;
    private boolean isShowSwitch;
    private boolean isConfigOpen;

    // 构造函数
    public SettingItem(String title, String content, boolean isShowSwitch, boolean isConfigOpen) {
        this.title = title;
        this.content = content;
        this.isShowSwitch = isShowSwitch;
        this.isConfigOpen = isConfigOpen;
    }

    // Getter 和 Setter 方法
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isShowSwitch() {
        return isShowSwitch;
    }

    public void setShowSwitch(boolean showSwitch) {
        isShowSwitch = showSwitch;
    }

    public boolean isConfigOpen() {
        return isConfigOpen;
    }

    public void setConfigOpen(boolean configOpen) {
        isConfigOpen = configOpen;
    }


}


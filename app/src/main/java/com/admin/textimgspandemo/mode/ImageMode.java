package com.admin.textimgspandemo.mode;

/**
 * Created by admin on 2018/1/12.
 */

public class ImageMode {
    private String name;
    private String avatarUrl;

    public ImageMode(String name, String avatarUrl) {
        this.name = name;
        this.avatarUrl = avatarUrl;
    }
    public ImageMode() {

    }
    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

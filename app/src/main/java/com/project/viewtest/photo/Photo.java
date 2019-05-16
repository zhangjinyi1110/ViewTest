package com.project.viewtest.photo;

import android.net.Uri;

public class Photo implements Media {

    private String name;
    private String desc;
    private String date;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public Uri getUri() {
        return MediaType.IMAGE;
    }
}

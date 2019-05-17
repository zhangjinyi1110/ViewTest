package com.project.viewtest.photo;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

public class Photo implements Media, Parcelable {

    private String name;
    private String desc;
    private String date;
    private String path;

    public Photo(){}

    public Photo(Parcel in) {
        name = in.readString();
        desc = in.readString();
        date = in.readString();
        path = in.readString();
    }

    public static final Creator<Photo> CREATOR = new Creator<Photo>() {
        @Override
        public Photo createFromParcel(Parcel in) {
            return new Photo(in);
        }

        @Override
        public Photo[] newArray(int size) {
            return new Photo[size];
        }
    };

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(desc);
        dest.writeString(date);
        dest.writeString(path);
    }
}

package com.project.viewtest.photo;

import android.content.Context;

public class MediaHelper {

    public static MediaQuery<Photo> queryPhoto(Context context) {
        return new PhotoQuery(context);
    }

}

package com.project.viewtest.photo;

import android.net.Uri;
import android.provider.MediaStore;

public interface Media {

    class MediaType {

        public final static Uri IMAGE = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

//    public final static Uri IMAGE = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

    }

    Uri getUri();

}

package com.project.viewtest.photo;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.project.viewtest.utils.TimeUtils;

import java.util.ArrayList;
import java.util.List;

public class PhotoQuery extends MediaQuery<Photo> {

    PhotoQuery(Context context) {
        super(context);
    }

    @Override
    Uri getUri() {
        return Media.MediaType.IMAGE;
    }

    @Override
    List<Photo> query(int start, int count, boolean all) {
        List<Photo> photos = new ArrayList<>();
        Cursor cursor = getCursor();
        if (cursor == null) {
            return photos;
        }
        int position = all ? 0 : start;
        boolean flag = cursor.moveToPosition(position);
        if (!flag) {
            return photos;
        }
        while (photos.size() < count && cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME));
            long time = cursor.getLong(cursor.getColumnIndex(MediaStore.Images.Media.DATE_ADDED));
            String date = TimeUtils.long2string(time, "yyyy-MM-dd");
            Photo photo = new Photo();
            photo.setDate(date);
            photo.setName(name);
            photos.add(photo);
        }
        cursor.close();
        return photos;
    }

}

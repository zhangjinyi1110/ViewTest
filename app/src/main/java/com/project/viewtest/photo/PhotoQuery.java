package com.project.viewtest.photo;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.project.viewtest.utils.TimeUtils;

import java.util.ArrayList;

public class PhotoQuery extends MediaQuery<Photo> {

    PhotoQuery(Context context) {
        super(context);
    }

    @Override
    Uri getUri() {
        return Media.MediaType.IMAGE;
    }

    @Override
    ArrayList<Photo> query(int start, int count, boolean all) {
        ArrayList<Photo> photos = new ArrayList<>();
        Cursor cursor = getCursor();
        if (cursor == null) {
            return photos;
        }
        int position = all ? 0 : start;
        boolean flag = cursor.moveToPosition(position);
        if (!flag) {
            return photos;
        }
        while ((all || photos.size() < count) && cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME));
            String data = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            long time = cursor.getLong(cursor.getColumnIndex(MediaStore.Images.Media.DATE_ADDED));
            String date = TimeUtils.long2string(time, "yyyy-MM-dd");
            Photo photo = new Photo();
            photo.setDate(date);
            photo.setName(name);
            photo.setPath(data);
            photos.add(photo);
            Log.e("aaa", "query: " + name + "/" + data + "/" + date);
        }
        cursor.close();
        return photos;
    }

}

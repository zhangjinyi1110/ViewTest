package com.project.viewtest.photo;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.Arrays;
import java.util.List;

public class SelectPhoto {



    public static List<Photo> startSelect(Context context) {
        return null;
    }

    public static void get(Context context) {
        if (!check(context)) {
            return;
        }
        Cursor cursor = context.getContentResolver()
                .query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null, MediaStore.Images.Media.DEFAULT_SORT_ORDER);
//        List<>
        if (cursor != null) {
            while (cursor.moveToNext()) {
                //获取图片的名称
                String name = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME));
                //获取图片的名称
                String data = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                //获取图片的生成日期
                String date = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATE_ADDED));
                //获取图片的详细信息
//                String desc = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DESCRIPTION));
                Log.e("photo", "get: " + name + "/" /*+ desc + "/"*/ + date + "/" + data);
            }
            cursor.close();
        }
    }

    private static boolean check(Context context) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0x00);
            return false;
        }
        return true;
    }

}

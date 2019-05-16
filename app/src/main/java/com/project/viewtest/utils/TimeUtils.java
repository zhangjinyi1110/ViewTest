package com.project.viewtest.utils;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtils {

    public static String long2string(long time, String format) {
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(long2date(time));
    }

    public static Date long2date(long time) {
        return new Date(time);
    }

}

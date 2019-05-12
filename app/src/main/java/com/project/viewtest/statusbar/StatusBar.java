package com.project.viewtest.statusbar;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

public class StatusBar/* implements Bar*/ {

    //SYSTEM_UI_FLAG_FULLSCREEN---隐藏状态栏
    //SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN--显示状态栏
    //SYSTEM_UI_FLAG_LAYOUT_STABLE

    private final String TAG = "aaa";

    private Context context;

    public static Bar with(Context context) {
        Window window = ((Activity) context).getWindow();
        View d = window.getDecorView();
        ViewGroup contentView = d.findViewById(Window.ID_ANDROID_CONTENT);
        if (contentView.getChildCount() > 0) {
            View content = contentView.getChildAt(0);
            if (content instanceof Bar) {
                return (Bar) content;
            }
        }
        return new BarView(context);
    }



}

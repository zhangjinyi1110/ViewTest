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

public class StatusBar implements Bar {

    //SYSTEM_UI_FLAG_FULLSCREEN---隐藏状态栏
    //SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN--显示状态栏
    //SYSTEM_UI_FLAG_LAYOUT_STABLE

    private final String TAG = "aaa";

    private Context context;

    private StatusBar(Context context) {
        this.context = context;
    }

    public static StatusBar with(Context context) {
        return new StatusBar(context);
    }

    @Override
    public Bar setBackgroundColor(@ColorInt int color) {
        if (context instanceof Activity) {
            if (Build.VERSION_CODES.LOLLIPOP <= Build.VERSION.SDK_INT) {
                ((Activity) context).getWindow().setStatusBarColor(color);
            }
        }
        return this;
    }

    @Override
    public Bar setBackgroundDrawable(Drawable drawable) {
        if (context instanceof Activity) {
            if (Build.VERSION_CODES.LOLLIPOP <= Build.VERSION.SDK_INT) {
//                Window window = ((Activity) context).getWindow();
//                if (window != null) {
//                    View view = window.getDecorView();
//
//                }
                ViewGroup mContentView = ((Activity) context).findViewById(Window.ID_ANDROID_CONTENT);
                View mChildView = mContentView.getChildAt(0);
                if (mChildView != null) {
                    mChildView.setBackground(drawable);
                    //注意不是设置 ContentView 的 FitsSystemWindows, 而是设置 ContentView 的第一个子 View . 使其不为系统 View 预留空间.
                    ViewCompat.setFitsSystemWindows(mChildView, true);
                }
            }
        }
        return this;
    }

    @Override
    public Bar hideActionBar() {
        if (context instanceof FragmentActivity) {
            ActionBar actionBar = ((AppCompatActivity) context).getSupportActionBar();
            if (actionBar != null) {
                actionBar.hide();
            }
        } else if (context instanceof Activity) {
            android.app.ActionBar actionBar = ((Activity) context).getActionBar();
            if (actionBar != null) {
                actionBar.hide();
            }
        }
        return this;
    }

    @Override
    public Bar hideStatusBar() {
        if (context instanceof Activity) {
            View view = ((Activity) context).getWindow().getDecorView();
            if (view != null) {
                view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
            }
        }
        return this;
    }

    @Override
    public Bar hideHalfStatusBar() {
        if (context instanceof Activity) {
            View view = ((Activity) context).getWindow().getDecorView();
            if (view != null) {
                if (Build.VERSION_CODES.LOLLIPOP <= Build.VERSION.SDK_INT) {
                    view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                    ((Activity) context).getWindow().setStatusBarColor(Color.TRANSPARENT);
                }
            }
        }
        return this;
    }

}

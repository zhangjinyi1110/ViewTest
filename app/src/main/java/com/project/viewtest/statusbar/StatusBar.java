package com.project.viewtest.statusbar;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class StatusBar implements Bar {

    //SYSTEM_UI_FLAG_FULLSCREEN---隐藏状态栏
    //SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN--显示状态栏
    //SYSTEM_UI_FLAG_LAYOUT_STABLE

    private Context context;

    private StatusBar(Context context) {
        this.context = context;
    }

    public static StatusBar with(Context context) {
        return new StatusBar(context);
    }

    @Override
    public Bar setBackgroundColor(@ColorRes int color) {
        if (context instanceof Activity) {
            View view = ((Activity) context).getWindow().getDecorView();
            if (view != null) {
                view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                if (Build.VERSION_CODES.LOLLIPOP <= Build.VERSION.SDK_INT) {
                    ((Activity) context).getWindow().setStatusBarColor(Color.TRANSPARENT);
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
                view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                if (Build.VERSION_CODES.LOLLIPOP <= Build.VERSION.SDK_INT) {
                    ((Activity) context).getWindow().setStatusBarColor(Color.TRANSPARENT);
                }
            }
        }
        return this;
    }

}

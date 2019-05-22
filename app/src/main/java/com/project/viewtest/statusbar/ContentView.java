package com.project.viewtest.statusbar;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;

public class ContentView extends LinearLayout implements StatusBar {

    private Activity activity;

    private StatusBarView statusBarView;

    private int option;

    ContentView(Context context) {
        super(context);
        this.activity = (Activity) context;
        init();
    }

    private void init() {
        setOrientation(VERTICAL);
        Window window = activity.getWindow();
        View d = window.getDecorView();
        ViewGroup contentView = d.findViewById(Window.ID_ANDROID_CONTENT);
        if (contentView.getChildCount() > 0) {
            View content = contentView.getChildAt(0);
            contentView.removeView(content);
            this.addView(content);
            contentView.addView(this, 0);
        }
        statusBarView = new StatusBarView(activity);
    }

    @Override
    public void setOrientation(int orientation) {
        super.setOrientation(VERTICAL);
    }

    @Override
    public StatusBar setStatusBackgroundColor(@ColorInt int color) {
        initStatusBar();
        statusBarView.setBackgroundColor(color);
        return this;
    }

    @Override
    public StatusBar setStatusBackgroundDrawable(Drawable drawable) {
        initStatusBar();
        statusBarView.setBackground(drawable);
        return this;
    }

    @Override
    public StatusBar hideActionBar() {
        if (activity instanceof AppCompatActivity) {
            ActionBar actionBar = ((AppCompatActivity) activity).getSupportActionBar();
            if (actionBar != null) {
                actionBar.hide();
            }
        }
        android.app.ActionBar actionBar = activity.getActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        return this;
    }

    @Override
    public StatusBar hideStatusBar() {
        statusBarView.setVisibility(GONE);
        option |= SYSTEM_UI_FLAG_FULLSCREEN;
        View view = activity.getWindow().getDecorView();
        if (view != null) {
            view.setSystemUiVisibility(option);
        }
        return this;
    }

    @Override
    public StatusBar hideHalfStatusBar() {
        initStatusBar();
        statusBarView.setVisibility(GONE);
        return this;
    }

    @Override
    public StatusBar lightColor() {
        View view = activity.getWindow().getDecorView();
        if (view != null && Build.VERSION_CODES.M <= Build.VERSION.SDK_INT) {
            view.setSystemUiVisibility(option | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        return this;
    }

    @Override
    public StatusBar darkColor() {
        View view = activity.getWindow().getDecorView();
        if (view != null) {
            view.setSystemUiVisibility(option);
        }
        return this;
    }

    private void initStatusBar() {
        if (statusBarView.getVisibility() == GONE) {
            return;
        }
        if (getChildAt(0) != statusBarView) {
            this.addView(statusBarView, 0);
        }
        View view = activity.getWindow().getDecorView();
        if (view != null) {
            if (Build.VERSION_CODES.LOLLIPOP <= Build.VERSION.SDK_INT) {
                option |= View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
                option |= View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
                view.setSystemUiVisibility(option);
                activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
            }
        }
    }

}

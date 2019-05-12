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

import java.util.HashMap;
import java.util.Map;

public class BarView extends LinearLayout implements Bar {

    private Context context;

    private StatusBarView statusBarView;

    private Map<String, Integer> options;

    public BarView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    private void init() {
        setOrientation(VERTICAL);
        Window window = getActivity().getWindow();
        View d = window.getDecorView();
        ViewGroup contentView = d.findViewById(Window.ID_ANDROID_CONTENT);
        if (contentView.getChildCount() > 0) {
            View content = contentView.getChildAt(0);
            contentView.removeView(content);
            this.addView(content);
            contentView.addView(this, 0);
        }
        statusBarView = new StatusBarView(context);
        options = new HashMap<>();
    }

    @Override
    public void setOrientation(int orientation) {
        super.setOrientation(VERTICAL);
    }

    private Activity getActivity() {
        return (Activity) context;
    }

    @Override
    public Bar setStatusBackgroundColor(@ColorInt int color) {
        initStatusBar();
        statusBarView.setBackgroundColor(color);
        return this;
    }

    @Override
    public Bar setStatusBackgroundDrawable(Drawable drawable) {
        initStatusBar();
        statusBarView.setBackground(drawable);
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
        statusBarView.setVisibility(GONE);
        options.put("hideStatusBar", View.SYSTEM_UI_FLAG_FULLSCREEN);
        return this;
    }

    @Override
    public Bar hideHalfStatusBar() {
        statusBarView.setVisibility(GONE);
        return this;
    }

    @Override
    public Bar lightColor() {
        if (context instanceof Activity) {
            View view = ((Activity) context).getWindow().getDecorView();
            if (view != null && Build.VERSION_CODES.M <= Build.VERSION.SDK_INT) {
                options.put("lightColor", View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
        }
        return this;
    }

    @Override
    public Bar darkColor() {
        if (context instanceof Activity) {
            View view = ((Activity) context).getWindow().getDecorView();
            if (view != null && Build.VERSION_CODES.M <= Build.VERSION.SDK_INT) {
                options.remove("lightColor");
            }
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
        if (context instanceof Activity) {
            View view = ((Activity) context).getWindow().getDecorView();
            if (view != null) {
                if (Build.VERSION_CODES.LOLLIPOP <= Build.VERSION.SDK_INT) {
                    options.put("initStatusBar", View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                    ((Activity) context).getWindow().setStatusBarColor(Color.TRANSPARENT);
                }
            }
        }
    }

    @Override
    public void build() {
        View view = ((Activity) context).getWindow().getDecorView();
        if (view != null) {
            int option = 0;
            for (String key : options.keySet()) {
                option |= options.get(key);
            }
            view.setSystemUiVisibility(option);
        }
    }
}

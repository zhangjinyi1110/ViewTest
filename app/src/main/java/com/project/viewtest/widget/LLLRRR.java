package com.project.viewtest.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

public class LLLRRR extends RecyclerView {

    private final String TAG = getClass().getSimpleName();

    public LLLRRR(Context context) {
        super(context);
    }

    public LLLRRR(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LLLRRR(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.e(TAG, "dispatchTouchEvent: " + ev.getAction());
        boolean flag = super.dispatchTouchEvent(ev);
        Log.e(TAG, "dispatchTouchEvent: " + flag);
        return flag;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e(TAG, "onTouchEvent: " + event.getAction());
        boolean flag = super.onTouchEvent(event);
        Log.e(TAG, "onTouchEvent: " + flag);
        return flag;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.e(TAG, "onInterceptTouchEvent: " + ev.getAction());
        boolean flag = super.onInterceptTouchEvent(ev);
        Log.e(TAG, "onInterceptTouchEvent: " + flag);
        return flag;
    }
}

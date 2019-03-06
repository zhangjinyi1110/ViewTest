package com.project.viewtest.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

public class LLL extends LinearLayout {

    private final String TAG = getClass().getSimpleName();

    public LLL(Context context) {
        super(context);
        setClickable(true);
    }

    public LLL(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setClickable(true);
    }

    public LLL(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setClickable(true);
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
//        Log.e(TAG, "onInterceptTouchEvent: " + ev.getAction());
//        boolean flag = super.onInterceptTouchEvent(ev);
//        Log.e(TAG, "onInterceptTouchEvent: " + flag);
        return true;
    }
}

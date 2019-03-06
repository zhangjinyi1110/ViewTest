package com.project.viewtest.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class HeaderLayout extends LinearLayout {

    private Context context;
    private View header;
    private View content;
    private LayoutParams layoutParams;

    private int scroll = 0;

    public HeaderLayout(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public HeaderLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public HeaderLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    private void init() {
        setOrientation(VERTICAL);
        layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (getChildCount() > 2) {
            removeViews(2, getChildCount());
        } else if (getChildCount() > 0) {
            header = getChildAt(0);
            content = getChildAt(1);
            header.setLayoutParams(layoutParams);
            content.setLayoutParams(layoutParams);
        }
        measure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.getSize(heightMeasureSpec));
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            int width = view.getMeasuredWidth();
            int height = view.getMeasuredHeight();
            switch (i) {
                case 0:
                    view.layout(0, scroll - height, width, scroll);
                    break;
                case 1:
                    view.layout(0, scroll, width, height);
                    break;
            }
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void setOrientation(int orientation) {
        super.setOrientation(VERTICAL);
    }
}

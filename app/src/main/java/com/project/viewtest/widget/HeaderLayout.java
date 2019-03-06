package com.project.viewtest.widget;

import android.animation.TimeInterpolator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.project.viewtest.utils.SizeUtils;

public class HeaderLayout extends LinearLayout {

    private Context context;
    private View header;
    private View content;
    private LayoutParams layoutParams;

    private int scroll = 0;
    private int start = 0;

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
        measureChildren(widthMeasureSpec, heightMeasureSpec);
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
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                start = (int) ev.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                scroll = (int) (ev.getX() - start);
                requestLayout();
                break;
            case MotionEvent.ACTION_UP:
                scroll = (int) (ev.getX() - start);
                if (Math.abs(scroll) >= isScroll()) {
                    startAnim(scroll, getMeasuredHeight());
                } else {
                    startAnim(scroll, 0);
                }
                break;
        }
        return true;
    }

    private void startAnim(float start, float end) {
        ValueAnimator animator = ValueAnimator.ofFloat(start, end);
        animator.setDuration(1000);
        animator.setEvaluator(new TypeEvaluator<Float>() {
            @Override
            public Float evaluate(float fraction, Float startValue, Float endValue) {
                return fraction * Math.abs(endValue - startValue);
            }
        });
        animator.setInterpolator(new TimeInterpolator() {
            @Override
            public float getInterpolation(float input) {
                return input;
            }
        });
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                scroll = (int) animation.getAnimatedValue();
                requestLayout();
            }
        });
    }

    private int isScroll() {
        return SizeUtils.dp2px(context, 50);
    }

    @Override
    public void setOrientation(int orientation) {
        super.setOrientation(VERTICAL);
    }
}

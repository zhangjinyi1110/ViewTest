package com.project.viewtest.widget;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.project.viewtest.R;
import com.project.viewtest.utils.SizeUtils;

public class ScrollMoreView extends FrameLayout {

    private Context context;
    private LinearLayout btnLayout;
    private FrameLayout content;
    private TextView ok;
    private TextView cancel;
    private TextView more;

    private int start = 0;
    private int move = 0;

    private final String TAG = getClass().getSimpleName();

    public ScrollMoreView(@NonNull Context context) {
        super(context);
        this.context = context;
        init();
    }

    public ScrollMoreView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public ScrollMoreView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    private void init() {
        btnLayout = new LinearLayout(context);
        btnLayout.setBackgroundColor(context.getResources().getColor(R.color.colorBlack));
        ok = new TextView(context);
        ok.setText("确定");
        ok.setGravity(Gravity.CENTER);
        ok.setBackgroundColor(context.getResources().getColor(R.color.colorAccent));
        ok.setTextColor(context.getResources().getColor(R.color.colorWhite));
        ok.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1));
        cancel = new TextView(context);
        cancel.setText("取消");
        cancel.setGravity(Gravity.CENTER);
        cancel.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
        cancel.setTextColor(context.getResources().getColor(R.color.colorWhite));
        cancel.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1));
        more = new TextView(context);
        more.setText("更多");
        more.setGravity(Gravity.CENTER);
        more.setBackgroundColor(context.getResources().getColor(R.color.colorRed));
        more.setTextColor(context.getResources().getColor(R.color.colorWhite));
        more.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1));
        btnLayout.addView(ok);
        btnLayout.addView(cancel);
        btnLayout.addView(more);
        content = new FrameLayout(context);
        content.setBackgroundColor(context.getResources().getColor(R.color.colorWhite));
        addView(content);
        addView(btnLayout);
        setClickable(true);
        Log.e(TAG, "init: " + getChildCount());
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.e(TAG, "onMeasure: " + getChildCount());
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.EXACTLY
                ? MeasureSpec.getSize(heightMeasureSpec) : SizeUtils.dp2px(context, 70);
        btnLayout.setLayoutParams(new LinearLayout.LayoutParams(getBtnWidth(), height));
        for (int i = 2; i < getChildCount(); i++) {
            if (i == 2) {
                View view = getChildAt(i);
                removeView(view);
                LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
                if (layoutParams.width == LayoutParams.MATCH_PARENT) {
                    layoutParams.width = width;
                }
                if (layoutParams.height == LayoutParams.MATCH_PARENT) {
                    layoutParams.height = height;
                }
                content.addView(view, layoutParams);
            } else {
                removeView(getChildAt(i));
            }
        }
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        Log.e(TAG, "onLayout: " + getChildCount());
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            if (view == btnLayout) {
                btnLayout.layout(width + move, 0, width + view.getMeasuredWidth() + move, height);
            } else if (view == content) {
                content.layout(move, 0, width + move, bottom);
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                start = (int) (event.getX() - move);
                break;
            case MotionEvent.ACTION_UP:
                if (isScroll(2))
                    moveTo(move, 0);
                else
                    moveTo(move, -getBtnWidth());
                break;
            case MotionEvent.ACTION_MOVE:
                move = (int) (event.getX() - start);
                if (move > 0)
                    move = 0;
                else if (move < -getBtnWidth())
                    move = -getBtnWidth();
                requestLayout();
                break;
        }
        return true;
    }

    private void moveTo(int start, int end) {
        ValueAnimator animator = ValueAnimator.ofInt(start, end);
        animator.setDuration(Math.abs(start - end));
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                ScrollMoreView.this.start = 0;
                move = (int) valueAnimator.getAnimatedValue();
                requestLayout();
            }
        });
        animator.start();
    }

    private boolean isScroll(int percent) {
        return move > -getBtnWidth() / 3 * percent;
    }

    private int getBtnWidth() {
        return getMeasuredWidth() / 5 * 2;
    }

    @Override
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams lp) {
        return new LayoutParams(lp);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(context, attrs);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
    }
}

package com.project.viewtest.widget;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
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
    private LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

    private int scroll = 0;//滑动距离
    private int start = 0;//开始点

    private boolean showHeader = false;//是否打开了头部

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
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //除了头部和内容View都移除
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            if (i < 2) {
                view.setLayoutParams(layoutParams);
                continue;
            }
            removeView(view);
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
    public boolean dispatchTouchEvent(MotionEvent ev) {
        //只有两个view时才执行
        if (getChildCount() == 2)
            switch (ev.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    start = (int) ev.getY();//获取开始点
                    break;
                case MotionEvent.ACTION_UP:
                    break;
                case MotionEvent.ACTION_MOVE:
                    break;
            }
        return super.dispatchTouchEvent(ev);
    }

    //打开头部时已经是滑动了一个屏幕的长度，没打开时为0
    private int getScrollHeight() {
        return showHeader ? getMeasuredHeight() : 0;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        //只有两个view时才执行
        if (getChildCount() == 2)
            switch (ev.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    break;
                case MotionEvent.ACTION_UP:
                    break;
                case MotionEvent.ACTION_MOVE:
                    scroll = (int) (ev.getY() - start) + getScrollHeight();
                    //没打开且contentView已经到达顶部且向下滑动5dp才能判断是想要下拉
                    if (!showHeader && isTop(getChildAt(1)) && scroll > SizeUtils.dp2px(context, 5))
                        return true;
                    else if (showHeader) {//打开头部后直接true
                        return true;
                    }
                    break;
            }
        return super.onInterceptTouchEvent(ev);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_UP:
                scroll = (int) (ev.getY() - start) + getScrollHeight();//加上是否打开的长度
                if (showHeader && scroll < getScrollHeight()) {//打开且向上拉
                    if (Math.abs(scroll - getScrollHeight()) >= isScroll()) {//超过指定距离，自己滑动到顶部
                        showHeader = false;//设为没打开
                        startAnim(scroll, getScrollHeight());
                    } else {//否则回到原点
                        startAnim(scroll, getScrollHeight());
                    }
                } else if (scroll > getScrollHeight() && !showHeader) {//没打开且上下拉
                    if (Math.abs(scroll) >= isScroll()) {//超过指定距离，自己滑动到底部
                        showHeader = true;//设为打开
                        startAnim(scroll, getScrollHeight());
                    } else {
                        startAnim(scroll, getScrollHeight());
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:
                scroll = (int) (ev.getY() - start) + getScrollHeight();//加上是否打开的长度
                if (showHeader) {
                    if (scroll < getScrollHeight()) {
                        //打开且向上拉
                        requestLayout();
                    }
                } else {
                    //没打开且向上滑动时将滑动距离致为没打开的距离
                    if (scroll < getScrollHeight())
                        scroll = getScrollHeight();
                    requestLayout();
                }
                break;
        }
        return true;
    }

    //动画
    private void startAnim(int start, int end) {
        ValueAnimator animator = ValueAnimator.ofInt(start, end);
        animator.setDuration(Math.abs(start - end) / 3);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                scroll = (int) animation.getAnimatedValue();
                requestLayout();
            }
        });
        animator.start();
    }

    //指定超过此距离自行滑动
    private int isScroll() {
        return SizeUtils.dp2px(context, 100);
    }

    @Override
    public void setOrientation(int orientation) {
        super.setOrientation(VERTICAL);
    }

    //判断是否到达顶部
    private boolean isTop(View view) {
        if (view.canScrollVertically(-1))
            return false;
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                if (!isTop(((ViewGroup) view).getChildAt(i)))
                    return false;
            }
        }
        return true;
    }
}

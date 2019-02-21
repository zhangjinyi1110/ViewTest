package com.project.viewtest.widget;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
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

public class ScrollView extends FrameLayout {

    private Context context;
    private FrameLayout content;
    private LinearLayout btnLayout;
    private TextView ok;
    private TextView cancel;
    private TextView more;

    private OnItemClickListener itemClickListener;

    private int start = 0;
    private int scroll = 0;
    private boolean isBtnClose = false;

    public final static int TYPE_OK = 0;
    public final static int TYPE_CANCEL = 1;
    public final static int TYPE_MORE = 2;

    private final String TAG = getClass().getSimpleName();

    public ScrollView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public ScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public ScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
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
        ok.setSingleLine();
        ok.setBackgroundColor(context.getResources().getColor(R.color.colorAccent));
        ok.setTextColor(context.getResources().getColor(R.color.colorWhite));
        ok.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1));
        cancel = new TextView(context);
        cancel.setText("取消");
        cancel.setGravity(Gravity.CENTER);
        cancel.setSingleLine();
        cancel.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
        cancel.setTextColor(context.getResources().getColor(R.color.colorWhite));
        cancel.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1));
        more = new TextView(context);
        more.setText("更多");
        more.setGravity(Gravity.CENTER);
        more.setSingleLine();
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
        more.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (itemClickListener != null)
                    itemClickListener.onItemClick(view, TYPE_MORE);
            }
        });
        ok.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (itemClickListener != null)
                    itemClickListener.onItemClick(view, TYPE_OK);
            }
        });
        cancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (itemClickListener != null)
                    itemClickListener.onItemClick(view, TYPE_CANCEL);
            }
        });
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.EXACTLY
                ? MeasureSpec.getSize(heightMeasureSpec) : SizeUtils.dp2px(context, 70);
        btnLayout.setLayoutParams(new LayoutParams(-scroll, height));
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        for (int j = 0; j < getChildCount(); j++) {
            View view = getChildAt(j);
            if (view == btnLayout) {
                btnLayout.layout(width + scroll, 0, width, height);
            } else if (view == content) {
                content.layout(scroll, 0, width + scroll, height);
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
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

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.e(TAG, "dispatchTouchEvent: " + ev.getAction());
        if (isBtnClose)
            return onTouchEvent(ev);
        return super.dispatchTouchEvent(ev);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        Log.e(TAG, "onTouchEvent: ");
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                start = (int) (ev.getX() - scroll);
                break;
            case MotionEvent.ACTION_UP:
                if (isScroll(2)) {
                    normalPosition(scroll, 0);
                } else {
                    normalPosition(scroll, -getBtnWidth());
                }
                break;
            case MotionEvent.ACTION_MOVE:
                scroll = (int) (ev.getX() - start);
                if (scroll > 0)
                    scroll = 0;
                else if (scroll < -getBtnWidth())
                    scroll = -getBtnWidth();
                requestLayout();
                break;
        }
        return true;
    }

    private void normalPosition(int start, int end) {
        ValueAnimator animator = ValueAnimator.ofInt(start, end);
        animator.setDuration(Math.abs(start - end));
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                ScrollView.this.start = 0;
                scroll = (int) valueAnimator.getAnimatedValue();
                requestLayout();
            }
        });
        animator.start();
    }

    private int getBtnWidth() {
        return getMeasuredWidth() / 5 * 2;
    }

    private boolean isScroll(int percent) {
        return scroll > -getBtnWidth() / 3 * percent;
    }

    public void setShowCancel(boolean showCancel) {
        btnLayout.getChildAt(1).setVisibility(showCancel ? VISIBLE : GONE);
    }

    public void setShowMore(boolean showMore) {
        btnLayout.getChildAt(2).setVisibility(showMore ? VISIBLE : GONE);
    }

    public void setShowOk(boolean showOk) {
        btnLayout.getChildAt(0).setVisibility(showOk ? VISIBLE : GONE);
    }

    public void setOk(View ok) {
        btnLayout.removeView(this.ok);
        btnLayout.addView(ok, 0);
        ok.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1));
        ok.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (itemClickListener != null)
                    itemClickListener.onItemClick(view, TYPE_OK);
            }
        });
    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int id);
    }

    public void setBtnClose(boolean btnClose) {
        isBtnClose = btnClose;
    }
}

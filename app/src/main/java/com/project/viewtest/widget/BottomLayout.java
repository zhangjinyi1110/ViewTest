package com.project.viewtest.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2018/11/16.
 * 底部导航栏
 */

public class BottomLayout extends ViewGroup {

    private Context context;
    private final String TAG = BottomLayout.class.getSimpleName();
    private Paint paint;
    private OnChangeItemListener listener;
    private int currView = -1;

    public BottomLayout(Context context) {
        super(context);
        this.context = context;
        init(null, 0);
    }

    public BottomLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init(attrs, 0);
    }

    public BottomLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init(attrs, defStyleAttr);
    }

    private void init(AttributeSet attrs, int defStyleAttr) {
        paint = new Paint();
        setClickable(true);
    }

    public void setChangeItemListener(OnChangeItemListener listener) {
        this.listener = listener;
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            if (!(view instanceof BottomView)) {
                throw new NullPointerException();
            }
            measureChild(view, widthMeasureSpec, heightMeasureSpec);
            if (height == 0) {
                height = view.getMeasuredHeight();
            }
        }
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int count = getChildCount();
        int itemWidth = getMeasuredWidth() / count;
        int height = getMeasuredHeight();
        for (int i = 0; i < count; i++) {
            View view = getChildAt(i);
            view.layout(itemWidth * i, 0, itemWidth * (i + 1), height);
            final int finalI = i;
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i(TAG, "onClick: " + finalI);
                    if (currView != -1) {
                        int last = currView;
                        if (last == finalI) {
                            return;
                        }
                        Log.i(TAG, "onClick: false");
                        ((BottomView) getChildAt(last)).changeColor(false);
                    }
                    currView = finalI;
                    ((BottomView) v).changeColor(true);
                    if (listener != null) {
                        listener.onChange(v, finalI);
                    }
                }
            });
        }
    }

    public void setCurr(final int i) {
        if (i < 0 && i > getChildCount() - 1) {
            throw new ArrayIndexOutOfBoundsException("The layout have " + getChildCount() + "child view");
        }
        this.post(new Runnable() {
            @Override
            public void run() {
                Log.i(TAG, "setCurr: " + i);
                getChildAt(i).performClick();
            }
        });
    }

    public interface OnChangeItemListener {
        void onChange(View view, int position);
    }

    @SuppressLint("ClickableViewAccessibility")
    public void setViewPager(final ViewPager viewPager) {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            private int state;
            private int currPager;

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                switch (state) {
                    case 1:
                        if (position == currPager) {
                            if (currView != 0) {
                                getItem(currView - 1).initColor();
                            }
                            getItem(currView).setColor(positionOffset);
                            if (currView != getChildCount() - 1)
                                getItem(currView + 1).setColor(1 - positionOffset);
                        } else if (position < currPager) {
                            if (currView != getChildCount() - 1) {
                                getItem(currView + 1).initColor();
                            }
                            getItem(currView).setColor(1 - positionOffset);
                            if (currView != 0)
                                getItem(currView - 1).setColor(positionOffset);
                        }
                        break;
                    case 2:
                        Log.i(TAG, "onPageScrolled: " + position + "/" + currPager);
                        if (position == currPager) {
                            getItem(currView).setColor(positionOffset);
                            if (currView != getChildCount() - 1)
                                getItem(currView + 1).setColor(1 - positionOffset);
                        } else if (position < currPager) {
                            getItem(currView).setColor(1 - positionOffset);
                            if (currView != 0)
                                getItem(currView - 1).setColor(positionOffset);
                        }
                        break;
                    case 0:
//                        currView = viewPager.getCurrentItem();
                        break;
                }
                Log.i(TAG, "onPageScrolled: " + position + "/" + viewPager.getCurrentItem() + "/" + positionOffset + "/" + positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                Log.i(TAG, "onPageSelected: " + position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                this.state = state;
                if (state == 0) {
                    currView = viewPager.getCurrentItem();
                } else if (state == 1) {
                    currPager = viewPager.getCurrentItem();
                }
                Log.i(TAG, "onPageScrollStateChanged: " + state + "/" + viewPager.getCurrentItem());
            }
        });
    }

    private BottomView getItem(int position) {
        return (BottomView) getChildAt(position);
    }

}
package com.project.viewtest.widget;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
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
    private FrameLayout content;//内容
    private LinearLayout btnLayout;//button容器
    private TextView ok;//确定键
    private TextView cancel;//取消键
    private TextView more;//更多键

    private OnItemClickListener itemClickListener;//点击button容器里的view的回调

    private int start = 0;//开始点击的x坐标
    private int scroll = 0;//滑动的距离
    private int tb = 0;//开始点击时的y坐标
    private long startTime = 0;
    private long endTime = 0;
    private boolean isBtnClose = false;//是否允许在button容器里滑动关闭
    private boolean isOpenBtn = false;//是否打开button容器
    private OnClickListener listener;

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

    @SuppressLint("ClickableViewAccessibility")
    private void init() {
        btnLayout = new LinearLayout(context);
        //ok键
        ok = new TextView(context);
        ok.setText("确定");
        ok.setGravity(Gravity.CENTER);
        ok.setSingleLine();
        ok.setBackgroundColor(context.getResources().getColor(R.color.colorAccent));
        ok.setTextColor(context.getResources().getColor(R.color.colorWhite));
        ok.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1));
        //cancel键
        cancel = new TextView(context);
        cancel.setText("取消");
        cancel.setGravity(Gravity.CENTER);
        cancel.setSingleLine();
        cancel.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
        cancel.setTextColor(context.getResources().getColor(R.color.colorWhite));
        cancel.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1));
        //more键
        more = new TextView(context);
        more.setText("更多");
        more.setGravity(Gravity.CENTER);
        more.setSingleLine();
        more.setBackgroundColor(context.getResources().getColor(R.color.colorRed));
        more.setTextColor(context.getResources().getColor(R.color.colorWhite));
        more.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1));
        //添加
        btnLayout.addView(ok);
        btnLayout.addView(cancel);
        btnLayout.addView(more);
        //中心内容view
        content = new FrameLayout(context);
        content.setBackgroundColor(context.getResources().getColor(R.color.colorWhite));
        //添加内容view和button容器
        addView(content);
        addView(btnLayout);
        setClickable(true);
        //设置回调
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
//        ok.setOnTouchListener(touchListener);
//        cancel.setOnTouchListener(touchListener);
//        more.setOnTouchListener(touchListener);
    }

//    private OnTouchListener touchListener = new OnTouchListener() {
//        @SuppressLint("ClickableViewAccessibility")
//        @Override
//        public boolean onTouch(View v, MotionEvent event) {
//            return false;
//        }
//    };

    @SuppressLint("DrawAllocation")
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.EXACTLY
                ? MeasureSpec.getSize(heightMeasureSpec) : SizeUtils.dp2px(context, 70);
        btnLayout.setLayoutParams(new LayoutParams(-scroll, height));
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
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.e(TAG, "onInterceptTouchEvent: " + ev.getAction());
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.e(TAG, "dispatchTouchEvent: " + ev.getAction());
        //判断是否打开button容器和是否允许button容器关闭
        if ((isBtnClose && isOpenBtn) || listener != null) {
            switch (ev.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    start = (int) (ev.getX() - scroll);
                    tb = (int) ev.getY();
                    startTime = System.currentTimeMillis();
                    getParent().requestDisallowInterceptTouchEvent(true);
                    break;
                case MotionEvent.ACTION_UP:
                    if ((int) (ev.getX()) == start && !isOpenBtn) {
                        if (listener != null) {
                            listener.onClick(content);
                        }
                        return true;
                    } else if (isOpenBtn && ev.getX() - (content.getMeasuredWidth() - getBtnWidth()) <= 0) {
                        if (listener != null) {
                            listener.onClick(content);
                        }
                        return true;
                    }
                case MotionEvent.ACTION_MOVE:
                    if (isTB(ev.getRawY())) {
                        getParent().requestDisallowInterceptTouchEvent(false);
                        return false;
                    } else {
                        getParent().requestDisallowInterceptTouchEvent(true);
                    }
                    //避免在button容器中消费了事件
                    if (((int) ev.getX() - start + getBtnWidth()) <= 0) {
                        return super.dispatchTouchEvent(ev);
                    } else {
                        return onTouchEvent(ev);
                    }
                case MotionEvent.ACTION_CANCEL:
                    return onTouchEvent(ev);
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                start = (int) (ev.getX() - scroll);
                tb = (int) ev.getY();
                startTime = System.currentTimeMillis();
                break;
            case MotionEvent.ACTION_UP:
                endTime = System.currentTimeMillis();
                if (endTime - startTime <= 300) {
                    int off = (int) ev.getX() - start + scroll;
                    if (Math.abs(off) >= getBtnWidth() / 3 && !isOpenBtn) {
                        isOpenBtn = true;
                        normalPosition(scroll, -getBtnWidth());
                        break;
                    }
                }
                startTime = 0;
            case MotionEvent.ACTION_CANCEL:
                //超过三分之二时打开，否则关闭
                if (isScroll(2)) {
                    isOpenBtn = false;
                    normalPosition(scroll, 0);
                } else {
                    isOpenBtn = true;
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

    private boolean isTB(float evY) {
        int lt[] = new int[2];
        getLocationInWindow(lt);
        Log.e(TAG, "isTB: " + evY + "/" + lt[1] + "/" + getMeasuredHeight());
        return !(evY >= lt[1] && evY <= lt[1] + getMeasuredHeight());
    }

    //获取button容器最大长度
    private int getBtnWidth() {
        return getMeasuredWidth() / 5 * 2;
    }

    //判断是否要滑动
    private boolean isScroll(int percent) {
        return scroll > -getBtnWidth() / 3 * percent;
    }

    //设置内容view
    public void setContentView(View content) {
        this.content.removeAllViews();
        this.content.addView(content);
    }

    //是否显示cancel键
    public void setShowCancel(boolean showCancel) {
        setShow(showCancel, 1);
    }

    //是否显示more键
    public void setShowMore(boolean showMore) {
        setShow(showMore, 2);
    }

    //是否显示ok键
    public void setShowOk(boolean showOk) {
        setShow(showOk, 0);
    }

    private void setShow(boolean show, int index) {
        btnLayout.getChildAt(index).setVisibility(show ? VISIBLE : GONE);
    }

    public void setOkText(String text) {
        setBtnText(text, 0);
    }

    public void setCancelText(String text) {
        setBtnText(text, 1);
    }

    public void setMoreText(String text) {
        setBtnText(text, 2);
    }

    private void setBtnText(String text, int index) {
        View view = btnLayout.getChildAt(index);
        if (view instanceof TextView) {
            ((TextView) view).setText(text);
        }
    }

    public void setOkTextColor(int color) {
        setBtnTextColor(color, 0);
    }

    public void setCancelTextColor(int color) {
        setBtnTextColor(color, 1);
    }

    public void setMoreTextColor(int color) {
        setBtnTextColor(color, 2);
    }

    private void setBtnTextColor(int color, int index) {
        View view = btnLayout.getChildAt(index);
        if (view instanceof TextView) {
            ((TextView) view).setTextColor(context.getResources().getColor(color));
        }
    }

    public void setOkBackgroundColor(int color) {
        setBtnBackgroundColor(color, 0);
    }

    public void setCancelBackgroundColor(int color) {
        setBtnBackgroundColor(color, 1);
    }

    public void setMoreBackgroundColor(int color) {
        setBtnBackgroundColor(color, 2);
    }

    private void setBtnBackgroundColor(int color, int index) {
        btnLayout.getChildAt(index).setBackgroundColor(context.getResources().getColor(color));
    }

    public void setOkBackground(Drawable drawable) {
        setBtnBackground(drawable, 0);
    }

    public void setCancelBackground(Drawable drawable) {
        setBtnBackground(drawable, 1);
    }

    public void setMoreBackground(Drawable drawable) {
        setBtnBackground(drawable, 2);
    }

    private void setBtnBackground(Drawable drawable, int index) {
        btnLayout.getChildAt(index).setBackground(drawable);
    }

    public void setOkBackgroundResource(int resource) {
        setBtnBackgroundResource(resource, 0);
    }

    public void setCancelBackgroundResource(int resource) {
        setBtnBackgroundResource(resource, 1);
    }

    public void setMoreBackgroundResource(int resource) {
        setBtnBackgroundResource(resource, 2);
    }

    private void setBtnBackgroundResource(int resource, int index) {
        btnLayout.getChildAt(index).setBackgroundResource(resource);
    }

    //设置item点击回调事件
    public void setItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    //是否允许button容器关闭
    public void setBtnClose(boolean btnClose) {
        isBtnClose = btnClose;
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int id);
    }

    public void openBtn() {
        if (isOpenBtn) {
            return;
        }
        isOpenBtn = true;
        scroll = -getBtnWidth();
        requestLayout();
    }

    public void closeBtn() {
        if (!isOpenBtn) {
            return;
        }
        isOpenBtn = false;
        scroll = 0;
        requestLayout();
    }

    public boolean isOpenBtn() {
        return isOpenBtn;
    }

    public void setContentListener(OnClickListener listener) {
        if (listener != null && content != null) {
            this.listener = listener;
            content.setOnClickListener(listener);
        }
    }
}

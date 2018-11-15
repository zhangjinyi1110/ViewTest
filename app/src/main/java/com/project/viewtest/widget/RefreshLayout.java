package com.project.viewtest.widget;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ProgressBar;

import com.project.viewtest.ConstantInterpolation;
import com.project.viewtest.R;
import com.project.viewtest.utils.SizeUtils;

/**
 * Created by Administrator on 2018/11/12.
 * 刷新View
 */

public class RefreshLayout extends ViewGroup {

    private final String TAG = RefreshLayout.class.getSimpleName();
    private Context context;
    private float move;
    private float down;
    private boolean refreshing;
    private boolean sliding;
    private boolean animing;
    private float refreshHeight;
    private View refreshView;
    private Paint paint;
    private float waveHeight;
    private float waveTailHeight;
    private float waveValue;
    private OnRefreshListener listener;

    public RefreshLayout(Context context) {
        super(context);
        this.context = context;
        init(null, 0);
    }

    public RefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init(attrs, 0);
    }

    public RefreshLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init(attrs, defStyleAttr);
    }

    private void init(AttributeSet attrs, int defStyleAttr) {
        setClickable(true);
        refreshHeight = SizeUtils.dp2px(context, 100);
        waveTailHeight = SizeUtils.dp2px(context, 70);
        ProgressBar textView = new ProgressBar(context);
        int size = SizeUtils.dp2px(context, 5);
        textView.setPadding(size, size, size, size);
        textView.setBackgroundResource(R.drawable.progress_bar_bg);
        textView.setLayoutParams(new LayoutParams(SizeUtils.dp2px(context, 35), SizeUtils.dp2px(context, 35)));
        refreshView = textView;
        addView(refreshView);
        paint = new Paint();
        paint.setColor(Color.BLUE);
        setWillNotDraw(false);
        refreshView.setVisibility(INVISIBLE);
        setChildrenDrawingOrderEnabled(true);
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        if (refreshing) {
            refreshView.setVisibility(VISIBLE);
            Log.i(TAG, "onDraw: ");
        }
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        float x1 = getWidth() / 5;
        float x2 = x1 * 4;
        float p = 40;
        Path path = new Path();
        if (move <= refreshView.getHeight()) {
            path.moveTo(x1, 0);
            path.quadTo(x1 * 3 / 2, move / 10, getWidth() / 2 - 30, move / 10 * 9);
            path.quadTo(getWidth() / 2, move, getWidth() / 2 + 30, move / 10 * 9);
            path.quadTo(x2 - x1 / 2, move / 10, x2, 0);
            path.close();
            canvas.drawPath(path, paint);
        } else if (move <= refreshHeight) {
            int waveH = refreshView.getHeight();
            path.moveTo(x1, 0);
            path.quadTo(x1 * 3 / 2, waveH / 10, getWidth() / 2 - p, waveH / 10 * 9);
            path.quadTo(getWidth() / 2, waveH, getWidth() / 2 + p, waveH / 10 * 9);
            path.quadTo(x2 - x1 / 2, waveH / 10, x2, 0);
            path.close();
            canvas.drawPath(path, paint);
            path.reset();
            path.moveTo(getWidth() / 2 - p, waveH / 10 * 9);
            path.quadTo(getWidth() / 2, waveH, refreshView.getLeft(), move - waveH / 2);
            path.lineTo(refreshView.getRight(), move - waveH / 2);
            path.quadTo(getWidth() / 2, waveH, getWidth() / 2 + p, waveH / 10 * 9);
            path.close();
            canvas.drawPath(path, paint);
            path.reset();
            path.addCircle(getWidth() / 2, move - waveH / 2, waveH / 2, Path.Direction.CW);
            canvas.drawPath(path, paint);
        } else {
            path.moveTo(x1, 0);
            path.quadTo(x1 * 3 / 2, waveHeight / 10, getWidth() / 2 - p, waveHeight / 10 * 9);
            path.quadTo(getWidth() / 2, waveHeight, getWidth() / 2 + p, waveHeight / 10 * 9);
            path.quadTo(x2 - x1 / 2, waveHeight / 10, x2, 0);
            path.close();
            canvas.drawPath(path, paint);
            if (animing) {
                float x = getWidth() / 2;
                float y = move - refreshView.getHeight() / 2;
                float radius = refreshView.getHeight() / 2;
                float point = y - waveTailHeight;
                if (move != getHeight() / 2) {
                    path.reset();
                    path.moveTo(x, point);
                    path.lineTo(x - radius, y);
                    path.lineTo(x + radius, y);
                    path.close();
                    canvas.drawPath(path, paint);
                    path.reset();
                    path.addCircle(x, y, radius, Path.Direction.CW);
                    canvas.drawPath(path, paint);
                }
                path.reset();
                path.addOval(new RectF(x - radius - waveValue, y - radius + waveValue, x + radius + waveValue, y + radius - waveValue), Path.Direction.CW);
                canvas.drawPath(path, paint);
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            measureChild(view, widthMeasureSpec, heightMeasureSpec);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected int getChildDrawingOrder(int childCount, int i) {
//        Log.i(TAG, "getChildDrawingOrder: " + i + "/" + childCount);
//        return super.getChildDrawingOrder(childCount, i);
        if(i==childCount-1){
            Log.i(TAG, "getChildDrawingOrder: " + 0 + "/" + childCount);
            return 0;
        }
        Log.i(TAG, "getChildDrawingOrder: " + 1 + "/" + childCount);
        return i + 1;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
//        super.onLayout(changed, l, t, r, b);
//        refreshView.layout(getMeasuredWidth() / 2 - refreshView.getMeasuredWidth() / 2, (int) (-refreshView.getMeasuredHeight() + move), getMeasuredWidth() / 2 + refreshView.getMeasuredWidth() / 2, (int) move);
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            if (view == refreshView) {
                refreshView.layout(getMeasuredWidth() / 2 - refreshView.getMeasuredWidth() / 2, (int) (-refreshView.getMeasuredHeight() + move), getMeasuredWidth() / 2 + refreshView.getMeasuredWidth() / 2, (int) move);
            } else {
                view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.i(TAG, "onTouchEvent: " + event.getAction());
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
//            View view = getChildAt(1);
//            Log.i(TAG, "onTouchEvent: " + (view instanceof RecyclerView) + "/" + view.getTop());
//            if (view.getTop() == 0 && !refreshing && !sliding && !animing) {
//                down(event.getY());
//            }
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            View view = getChildAt(1);
            if(view instanceof RecyclerView){
                if (!view.canScrollVertically(-1) && !refreshing && !sliding && !animing) {
                    down(event.getY());
                }
                if (sliding) {
                    move(event.getY());
                }
            }
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            if (sliding) {
                upAnim();
            } else if (!refreshing && !animing){
                down = 0;
                move = 0;
            }
        }
        Log.i(TAG, "dispatchTouchEvent: " + refreshing + "/" + sliding + "/" + animing);
        return refreshing || sliding || animing || super.dispatchTouchEvent(event);
    }

    private void move(float y) {
        move = y - down;
        if(move<0){
            Log.i(TAG, "move: " + move + "/" + down);
            sliding = false;
            return;
        }
        if (move > refreshHeight) {
            animing = true;
            sliding = false;
            anim();
        } else {
            requestLayout();
            postInvalidate();
        }
    }

    private void down(float y) {
        down = y;
        sliding = true;
    }

    private void upAnim() {
        float init = move < refreshView.getHeight() ? move : refreshView.getHeight();
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(init, 0, init / 3 * 2, 0, init / 2, 0);
        valueAnimator.setDuration(300);
        valueAnimator.setInterpolator(new ConstantInterpolation());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                move = (float) animation.getAnimatedValue();
                requestLayout();
                postInvalidate();
            }
        });
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                down = 0;
                move = 0;
                sliding = false;
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        valueAnimator.start();
    }

    private void anim() {
        float h = refreshView.getHeight();
        //顶部波浪动画
        final ValueAnimator waveAnim = ValueAnimator.ofFloat(h, 0, h / 3 * 2, 0, h / 3, 0);
        waveAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                waveHeight = (float) animation.getAnimatedValue();
            }
        });
        waveAnim.setInterpolator(new ConstantInterpolation());
        waveAnim.setDuration(500);
        //水滴头动画
        final ValueAnimator loadAnim = ValueAnimator.ofFloat(move, getHeight() / 2);
        loadAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                move = (float) animation.getAnimatedValue();
                requestLayout();
                postInvalidate();
            }
        });
        loadAnim.setDuration(300);
        loadAnim.setInterpolator(new ConstantInterpolation());
        //水滴尾动画
        ValueAnimator waveTailAnim = ValueAnimator.ofFloat(waveTailHeight, refreshView.getHeight() / 2);
        waveTailAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                waveTailHeight = (float) animation.getAnimatedValue();
            }
        });
        waveTailAnim.setDuration(300);
        waveTailAnim.setInterpolator(new ConstantInterpolation());
        //水滴波动动画
        float value = SizeUtils.dp2px(context, 3);
        ValueAnimator wavingAnim = ValueAnimator.ofFloat(0, value * 4, -value * 3, value * 2, -value, 0);
        wavingAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                waveValue = (float) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        wavingAnim.setDuration(700);
        wavingAnim.setInterpolator(new DecelerateInterpolator());
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(loadAnim).with(waveAnim).with(waveTailAnim).before(wavingAnim);
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                Log.i(TAG, "onAnimationStart: set ");
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Log.i(TAG, "onAnimationEnd: set ");
                waveValue = 0;
                animing = false;
                refreshing = true;
                postInvalidate();
                if (listener != null) {
                    listener.onRefresh();
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animatorSet.start();
    }

    public void setOnRefreshListener(OnRefreshListener listener) {
        this.listener = listener;
    }

    public void setRefresh(boolean refresh) {
        if (sliding && animing) {
            Log.i(TAG, "setRefresh: 1");
            return;
        }
        if ((!refreshing && !refresh) || (refresh && refreshing)) {
            Log.i(TAG, "setRefresh: 2");
            return;
        }
        Log.i(TAG, "setRefresh: " + refresh);
        if (refresh) {
            ValueAnimator valueAnimator = ValueAnimator.ofFloat(refreshHeight + 1);
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float curr = (float) animation.getAnimatedValue();
                    move(curr);
                }
            });
            valueAnimator.setDuration(300);
            valueAnimator.setInterpolator(new ConstantInterpolation());
            valueAnimator.start();
        } else {
            move = 0;
            down = 0;
            refreshing = false;
            refreshView.setVisibility(INVISIBLE);
            requestLayout();
            postInvalidate();
        }
    }

    public interface OnRefreshListener {

        void onRefresh();

    }

}

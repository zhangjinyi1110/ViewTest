package com.project.viewtest.widget;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.animation.AccelerateInterpolator;
import android.widget.TextView;

import com.project.viewtest.R;

/**
 * Created by Administrator on 2018/11/1.
 * RadialGradientView
 */

@SuppressLint("AppCompatCustomView")
public class RadialGradientView extends TextView {

    private Context context;
    private Paint paint;
    private final int RADIUS = 50;
    private int currRadius;
    private RadialGradient radialGradient;
    private float x, y;
    private ObjectAnimator animator;

    public RadialGradientView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public RadialGradientView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public RadialGradientView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    private void init() {
        paint = new Paint();
        setClickable(true);
        if (getPaddingBottom() == 0 && getPaddingEnd() == 0 && getPaddingLeft() == 0 && getPaddingRight() == 0 && getPaddingStart() == 0 && getPaddingTop() == 0) {
            float scale = context.getResources().getDisplayMetrics().density;
            int value = (int) (scale * 5);
            setPadding(value * 2, value, value * 2, value);
        }
        setGravity(Gravity.CENTER);
        if (getBackground() == null) {
            setBackgroundResource(R.drawable.radial_gradient_bg);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(x, y, currRadius, paint);
    }

    private void setRadius(int radius) {
        currRadius = radius;
        if (currRadius > 0) {
            radialGradient = new RadialGradient(x, y, radius, 0x00FFFFFF, 0xFF58FAAC, Shader.TileMode.CLAMP);
            paint.setShader(radialGradient);
        }
        postInvalidate();
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (animator != null && animator.isRunning()) {
                animator.cancel();
            }
            if (animator == null) {
                animator = ObjectAnimator.ofInt(this, "radius", RADIUS, Math.max(getWidth(), getHeight()));
                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        setRadius((int) animation.getAnimatedValue());
                    }
                });
                animator.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        setRadius(0);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
                animator.setInterpolator(new AccelerateInterpolator());
                animator.setDuration(300);
            }
            animator.start();
        } else {
            x = event.getX();
            y = event.getY();
            setRadius(RADIUS);
        }
        return super.onTouchEvent(event);
    }
}

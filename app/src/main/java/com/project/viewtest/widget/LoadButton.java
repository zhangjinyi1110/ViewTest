package com.project.viewtest.widget;

import android.animation.Animator;
import android.animation.TimeInterpolator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.project.viewtest.R;

/**
 * Created by Administrator on 2018/11/2.
 * loadButton
 */

public class LoadButton extends View {

    private Context context;
    private Paint paint;
    private Paint loadBPaint;
    private Paint loadTPaint;
    private int color;
    private float radius;
    private final float RADIUS = 90;
    private final float LENGTH = 450;
    private float length;
    private float currLen;
    private float currAngle;
    private final String TAG = LoadButton.class.getSimpleName();
    private Path buttonPath;
    private Path loadTPath;
    private Path loadBPath;
    private ValueAnimator buttonAnim;
    private ValueAnimator loadAnim;
    private String action;
    private final String ACTION_LOAD = "load";
    private final String ACTION_SUCCESS = "success";
    private final String ACTION_FAILURE = "failure";
    private final String ACTION_DEF = "def";
    private String text;
    private Paint textPaint;

    public LoadButton(Context context) {
        super(context);
        this.context = context;
        init(null, 0);
    }

    public LoadButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init(attrs, 0);
    }

    public LoadButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init(attrs, defStyleAttr);
    }

    private void init(AttributeSet attrs, int defStyleAttr) {
        final TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.LoadButton, defStyleAttr, 0);
        radius = array.getDimension(R.styleable.LoadButton_radius, RADIUS);
        length = array.getDimension(R.styleable.LoadButton_length, LENGTH);
        text = array.getString(R.styleable.LoadButton_message);
        array.recycle();
        if (text == null || text.isEmpty()) {
            text = "点击加载";
        }
        textPaint = new Paint();
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(70);
        textPaint.setStyle(Paint.Style.FILL);
        color = Color.GREEN;
        paint = new Paint();
        paint.setColor(color);
        loadBPaint = new Paint();
        loadBPaint.setColor(Color.GRAY);
        loadBPaint.setStrokeWidth(13);
        loadBPaint.setStyle(Paint.Style.STROKE);
        loadTPaint = new Paint();
        loadTPaint.setColor(Color.WHITE);
        loadTPaint.setStrokeWidth(13);
        loadTPaint.setStyle(Paint.Style.STROKE);
        setClickable(true);
        currLen = length;
        buttonPath = new Path();
        loadTPath = new Path();
        loadBPath = new Path();
        action = ACTION_DEF;
        initLoadAnim();
        initButtonAnim();
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!buttonAnim.isRunning() && ACTION_DEF.equals(action)) {
                    action = ACTION_LOAD;
                    buttonAnim.start();
                }
            }
        });
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        circle(canvas);
        switch (action) {
            case ACTION_LOAD:
                load(canvas);
                break;
            case ACTION_SUCCESS:
                success(canvas);
                break;
            case ACTION_FAILURE:
                failure(canvas);
                break;
            case ACTION_DEF:
                Rect rect = new Rect();
                textPaint.getTextBounds(text, 0, text.length(), rect);
                Paint.FontMetricsInt fontMetricsInt = textPaint.getFontMetricsInt();
                float x = getWidth() / 2 - rect.width() / 2;
                float y = getHeight() / 2 + (-fontMetricsInt.descent + (fontMetricsInt.descent - fontMetricsInt.ascent) / 2);
                canvas.drawText(text, x, y, textPaint);
                break;
        }
    }

    private void failure(Canvas canvas) {
        double angle = 45 * Math.PI / 180;
        loadTPath.reset();
        loadBPath.reset();
        loadTPaint.setColor(Color.RED);
        float x = getWidth() / 2;
        float y = getHeight() / 2;
        loadTPath.moveTo(x - (float) (radius * 0.6 * Math.cos(angle)), y - (float) (radius * 0.6 * Math.sin(angle)));
        loadTPath.lineTo(x + (float) (radius * 0.6 * Math.cos(angle)), y + (float) (radius * 0.6 * Math.sin(angle)));
        loadTPath.moveTo(x + (float) (radius * 0.6 * Math.cos(angle)), y - (float) (radius * 0.6 * Math.sin(angle)));
        loadTPath.lineTo(x - (float) (radius * 0.6 * Math.cos(angle)), y + (float) (radius * 0.6 * Math.sin(angle)));
        canvas.drawPath(loadTPath, loadTPaint);
    }

    private void success(Canvas canvas) {
        double angle = 30 * Math.PI / 180;
        loadTPath.reset();
        loadBPath.reset();
        loadTPaint.setColor(Color.WHITE);
        float x = getWidth() / 2;
        float y = getHeight() / 2;
        loadTPath.moveTo(x - (float) (radius * 0.6 * Math.cos(angle)), y + (float) (radius * 0.6 * Math.sin(angle)));
        loadTPath.lineTo(x, y + (float) (radius * 0.6));
        loadTPath.lineTo(x + (float) (radius * 0.6 * Math.cos(angle)), y - (float) (radius * 0.6 * Math.sin(angle)));
        canvas.drawPath(loadTPath, loadTPaint);
    }

    private void load(Canvas canvas) {
        float r = (float) (radius * 0.6);
        loadTPath.reset();
        loadBPath.reset();
        loadBPath.addCircle(getWidth() / 2, getHeight() / 2, r, Path.Direction.CW);
        canvas.drawPath(loadBPath, loadBPaint);
        loadTPath.addArc(new RectF(getWidth() / 2 - r, getHeight() / 2 - r, getWidth() / 2 + r, getHeight() / 2 + r), -90, currAngle);
        canvas.drawPath(loadTPath, loadTPaint);
    }

    private void circle(Canvas canvas) {
        float x = getWidth() / 2;
        buttonPath.reset();
        buttonPath.moveTo(x, 0);
        float a = x - currLen / 2;
        buttonPath.lineTo(a, 0);
        buttonPath.arcTo(new RectF(a - radius, 0, a + radius, getHeight()), -90, -180);
        buttonPath.lineTo(x, getHeight());
        float b = x + currLen / 2;
        buttonPath.lineTo(b, getHeight());
        buttonPath.arcTo(new RectF(b - radius, 0, b + radius, getHeight()), 90, -180);
        buttonPath.close();
        canvas.drawPath(buttonPath, paint);
    }

    private void initButtonAnim() {
        buttonAnim = ValueAnimator.ofFloat(0, length);
        buttonAnim.setDuration(300);
        buttonAnim.setInterpolator(new TimeInterpolator() {
            @Override
            public float getInterpolation(float input) {
                return input;
            }
        });
        buttonAnim.setEvaluator(new TypeEvaluator<Float>() {
            @Override
            public Float evaluate(float fraction, Float startValue, Float endValue) {
                return endValue - fraction * (endValue - startValue);
            }
        });
        buttonAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                currLen = (float) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        buttonAnim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                loadAnim.start();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    private void initLoadAnim() {
        loadAnim = ValueAnimator.ofInt(0, 720);
        loadAnim.setDuration(1500);
        loadAnim.setRepeatCount(ValueAnimator.INFINITE);
        loadAnim.setEvaluator(new TypeEvaluator<Integer>() {
            @Override
            public Integer evaluate(float fraction, Integer startValue, Integer endValue) {
                return (int) (fraction * (endValue - startValue));
            }
        });
        loadAnim.setInterpolator(new TimeInterpolator() {
            @Override
            public float getInterpolation(float input) {
                return input;
            }
        });
        loadAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int angle = (int) animation.getAnimatedValue();
                if (angle >= 360) {
                    loadTPaint.setColor(Color.GRAY);
                    loadBPaint.setColor(Color.WHITE);
                } else {
                    loadTPaint.setColor(Color.WHITE);
                    loadBPaint.setColor(Color.GRAY);
                }
                currAngle = angle % 360;
                postInvalidate();
            }
        });
        loadAnim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                postInvalidate();
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
    }

    public void setResult(boolean flag) {
        if (flag) {
            action = ACTION_SUCCESS;
        } else {
            action = ACTION_FAILURE;
        }
        loadAnim.cancel();
    }

    public void reset() {
        action = ACTION_DEF;
        currLen = length;
        currAngle = 0;
        loadBPaint.setColor(Color.GRAY);
        loadTPaint.setColor(Color.WHITE);
        if (loadAnim.isRunning()) {
            loadAnim.cancel();
        } else {
            postInvalidate();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int warpWidth = (int) (radius * 2 + length);
        int warpHeight = (int) (radius * 2);
        setMeasuredDimension(warpWidth, warpHeight);
    }
}

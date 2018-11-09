package com.project.viewtest.widget;

import android.animation.Animator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/11/6.
 * 歌词View
 */

public class LyricsView extends View {

    private Context context;
    private final String TAG = LyricsView.class.getSimpleName();
    private List<String> lyrics;
    private int position;
    private Paint paint;
    //    private Path path;
    private float maxHeight;
    private float maxTextSize;
    private float down;
    private float move;

    public LyricsView(Context context) {
        super(context);
        this.context = context;
        init(null, 0);
    }

    public LyricsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init(attrs, 0);
    }

    public LyricsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init(attrs, defStyleAttr);
    }

    private void init(AttributeSet attrs, int defStyleAttr) {
        setClickable(true);
        lyrics = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            lyrics.add("后即可发顺丰喝口水" + i);
        }
        position = 0;
        maxTextSize = 80;
        paint = new Paint();
        paint.setTextSize(maxTextSize);
        paint.setColor(Color.BLACK);
        Paint.FontMetricsInt fontMetricsInt = paint.getFontMetricsInt();
        maxHeight = fontMetricsInt.bottom - fontMetricsInt.top;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (int i = 0; i < lyrics.size(); i++) {
            canvasText(canvas, i);
        }
    }

    private void canvasText(Canvas canvas, int i) {
        float midpointX = getWidth() / 2;
        float midpointY = getHeight() / 2;
        float offset = (Math.abs(move) / maxHeight);
        int curr = position - (int) (move / maxHeight);
        if (move < 0) {
            if (i <= position) {
                offset *= -1;
            } else if (i <= curr) {
                float remainder = (Math.abs(move) % maxHeight) / maxHeight;
                offset = Math.abs(i - position) - Math.abs(curr - i) - remainder;
            }
        } else if (move > 0) {
            if (i >= position) {
                offset *= -1;
            } else if (i >= curr) {
                float remainder = (Math.abs(move) % maxHeight) / maxHeight;
                Log.i(TAG, "canvasText: " + remainder);
                offset = Math.abs(i - position) - Math.abs(curr - i) - remainder;
            }
        }
        float size = maxTextSize - (Math.abs(i - position) * 8) + offset * 8;
        paint.setTextSize(size);
        Rect rect = new Rect();
        String lyric = lyrics.get(i);
        paint.getTextBounds(lyric, 0, lyric.length(), rect);
        float x = midpointX - rect.width() / 2;
        Paint.FontMetricsInt fontMetricsInt = paint.getFontMetricsInt();
        float baseline = (fontMetricsInt.descent - fontMetricsInt.ascent) / 2 - fontMetricsInt.descent;
        float y = midpointY + baseline + (i - position) * maxHeight + move;
        canvas.drawText(lyric, x, y, paint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int warpHeight = (int) (maxHeight * 5);
        int height = MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.EXACTLY ? MeasureSpec.getSize(heightMeasureSpec) : warpHeight;
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), height);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            down = event.getY();
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            move = event.getY() - down;
            int remainder = Math.abs(move) % maxHeight >= maxHeight / 2 ? move > 0 ? 1 : -1 : 0;
            int movePosition = (int) (move / maxHeight) + remainder;
            position -= movePosition;
            if (position < 0) {
                float h = position * maxHeight + Math.abs(move) % maxHeight / maxHeight;
                anim(h);
            } else if (position > lyrics.size() - 1) {
                float h = (position - lyrics.size() + 1) * maxHeight + Math.abs(move) % maxHeight / maxHeight;
                Log.i(TAG, "onTouchEvent: " + h + "/" + position);
                anim(h);
            } else {
                move = 0;
                postInvalidate();
            }
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            move = event.getY() - down;
            postInvalidate();
        }
        return super.onTouchEvent(event);
    }

    private void anim(final float height) {
        ValueAnimator animator = ValueAnimator.ofFloat(height);
        animator.setInterpolator(new TimeInterpolator() {
            @Override
            public float getInterpolation(float input) {
                return input;
            }
        });
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                move = (float) animation.getAnimatedValue();
                Log.i("onTouchEvent", "onAnimationUpdate: " + move);
                postInvalidate();
            }
        });
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                move = 0;
                if (height < 0) {
                    position = 0;
                } else if (height > 0) {
                    position = lyrics.size() - 1;
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.setDuration(100);
        animator.start();
    }

    public void next() {
        if (position == lyrics.size() - 1) {
            return;
        }
        ValueAnimator animator = ValueAnimator.ofFloat(-maxHeight);
        animator.setDuration(100);
        animator.setInterpolator(new TimeInterpolator() {
            @Override
            public float getInterpolation(float input) {
                return input;
            }
        });
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                move = (float) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                move = 0;
                position++;
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.start();
    }

    public void last() {
        if (position == 0) {
            return;
        }
        ValueAnimator animator = ValueAnimator.ofFloat(maxHeight);
        animator.setDuration(100);
        animator.setInterpolator(new TimeInterpolator() {
            @Override
            public float getInterpolation(float input) {
                return input;
            }
        });
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                move = (float) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                move = 0;
                position--;
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.start();
    }
}

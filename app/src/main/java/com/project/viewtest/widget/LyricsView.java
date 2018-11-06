package com.project.viewtest.widget;

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

/**
 * Created by Administrator on 2018/11/6.
 * 歌词View
 */

public class LyricsView extends View {

    private Context context;
    private Paint currPaint;
    private Paint secondPaint;
    private Paint surplusPaint;
    private int position;
    private int currPosition;
    private int currHeight;
    private int secondHeight;
    private int surplusHeight;
    private float move;
    private float down;
    private final String TAG = LyricsView.class.getSimpleName();

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
        currPaint = new Paint();
        currPaint.setColor(Color.BLACK);
        currPaint.setTextSize(90);
        secondPaint = new Paint();
        secondPaint.setColor(Color.GRAY);
        secondPaint.setTextSize(75);
        surplusPaint = new Paint();
        surplusPaint.setColor(Color.GRAY);
        surplusPaint.setTextSize(60);
        position = 0;
        currPosition = position;
        setClickable(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < 30; i++) {
            canvasText(canvas, i);
        }
    }

    private void canvasText(Canvas canvas, int i) {
        Paint paint;
        paint = currPaint;
        paint.setTextSize(90 - (Math.abs(i - position) * 10));
        Rect rect = new Rect();
        String test = "阿法水电费水电费" + i;
        paint.getTextBounds(test, 0, test.length(), rect);
        Paint.FontMetricsInt fontMetricsInt = paint.getFontMetricsInt();
        float x = getWidth() / 2 - rect.width() / 2;
        float y = (getHeight() / 2 + ((fontMetricsInt.descent - fontMetricsInt.ascent) / 2 - fontMetricsInt.descent)) + (currHeight) * (i - position) + move;
        canvas.drawText(test, x, y, paint);
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int measureWidth = MeasureSpec.getSize(widthMeasureSpec);
        int measureHeight = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        currHeight = (currPaint.getFontMetricsInt().bottom - currPaint.getFontMetricsInt().top);
        secondHeight = (secondPaint.getFontMetricsInt().bottom - secondPaint.getFontMetricsInt().top);
        surplusHeight = (surplusPaint.getFontMetricsInt().bottom - surplusPaint.getFontMetricsInt().top);
//        Log.i("measure", "onMeasure: " + currHeight + "/" + (currPaint.getFontMetricsInt().bottom - currPaint.getFontMetricsInt().top) + "/" + (currPaint.getFontMetricsInt().descent - currPaint.getFontMetricsInt().ascent));
        int warpHeight = currHeight * 2 + secondHeight * 2 + surplusHeight * 2;
        setMeasuredDimension(measureWidth, heightMode == MeasureSpec.EXACTLY ? measureHeight : warpHeight);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            move = 0;
            currPosition = position;
            postInvalidate();
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            move = event.getY() - down;
            position = currPosition -((int) (move / currHeight) + (((move % currHeight) >= (currHeight / 2)) ? 1 : 0));
            Log.i(TAG, "onTouchEvent: " + (move / currHeight) + "/" + position + "/" + (((move % currHeight) >= (currHeight / 2)) ? 1 : 0));
            postInvalidate();
        } else if (event.getAction() == MotionEvent.ACTION_DOWN) {
            down = event.getY();
        }
        return super.onTouchEvent(event);
    }
}

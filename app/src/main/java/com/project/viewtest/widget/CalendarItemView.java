package com.project.viewtest.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.project.viewtest.R;

import java.util.Calendar;
import java.util.TimeZone;

public class CalendarItemView extends View {

    private int year;
    private int month;
    private int day;
    private int dayCount;
    private int weekDay;
    private Paint paint;
    private int itemW;
    private int padding;
    private int margin;
    private int currData = -1;
    private int clickData = -1;
    private int firstDayForWeek;

    public CalendarItemView(Context context) {
        super(context);
        init(null, 0);
    }

    public CalendarItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public CalendarItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr);
    }

    private void init(AttributeSet attrs, int defStyleAttr) {
        Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getDefault());
        int mYear = c.get(Calendar.YEAR); // 获取当前年份
        int mMonth = c.get(Calendar.MONTH);// 获取当前月份
        int mDay = c.get(Calendar.DAY_OF_MONTH);// 获取当前月份的日期号码
        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.CalendarItemView, defStyleAttr, 0);
        year = array.getInt(R.styleable.CalendarItemView_year, mYear);
        month = array.getInt(R.styleable.CalendarItemView_month, mMonth) + 1;
        day = array.getInt(R.styleable.CalendarItemView_day, mDay);
        array.recycle();
        if (month <= 7) {
            if (month == 2) {
                dayCount = 28 + ((year % 4 == 0) ? 1 : 0);
            } else {
                dayCount = (month % 2 == 0) ? 30 : 31;
            }
        } else {
            dayCount = (month % 2 == 0) ? 31 : 30;
        }
        c.set(year, month - 1, day);
        Log.e("aaa", "init: " + year + "/" + month + "/" + day);
        weekDay = c.get(Calendar.DAY_OF_WEEK);
        firstDayForWeek = weekDay - day % 7 + 1;
        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setTextSize(getContext().getResources().getDisplayMetrics().scaledDensity * 15);
        padding = (int) (getContext().getResources().getDisplayMetrics().density * 30);
        margin = (int) (getContext().getResources().getDisplayMetrics().density * 1);
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = 0;
                for (Rect rect : dayRect) {
                    boolean start = rect.contains(startX, startY);
                    boolean end = rect.contains(endX, endY);
                    if (start && end) {
                        Log.e("aaa", "onClick: ok");
//                        Path path = new Path();
//                        path.moveTo(rect.left, rect.top);
//                        path.lineTo(rect.right, rect.top);
//                        path.lineTo(rect.right, rect.bottom);
//                        path.lineTo(rect.left, rect.bottom);
//                        path.close();
                        clickData = i;
                        invalidate();
                        break;
                    }
                    Log.e("aaa", "onClick: " + i);
                    i++;
                }
            }
        });
        setBackgroundColor(Color.CYAN);
    }

    Rect[] dayRect;

    private int color[] = new int[]{Color.YELLOW, Color.BLACK, Color.BLUE, Color.GRAY, Color.GREEN, Color.WHITE, Color.CYAN};

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        Rect rect = new Rect();
        int h = 0;
        int x;
        int y;
        dayRect = new Rect[dayCount];
        int weekDay = firstDayForWeek;
        for (int i = weekDay; i < dayCount + weekDay; i++) {
            String dayNum = String.valueOf(i - weekDay + 1);
            paint.getTextBounds(dayNum, 0, dayNum.length(), rect);
            Paint.FontMetricsInt fontMetricsInt = paint.getFontMetricsInt();
            int half = (fontMetricsInt.descent - fontMetricsInt.ascent) / 2;
            if (i <= 7) {
                if (h == 0)
                    h += padding / 2;
                x = i * itemW - itemW / 2 - rect.width() / 2;
            } else {
                int a = i % 7;
                if (a == 1) {
                    h += padding;
                }
                x = (a == 0 ? 7 : a) * itemW - itemW / 2 - rect.width() / 2;
            }
            y = h + (half - fontMetricsInt.descent);
            dayRect[Integer.valueOf(dayNum) - 1] = new Rect(x - itemW / 2 + rect.width() / 2, h - padding / 2, x + itemW / 2 + rect.width() / 2, h + padding / 2);
            Log.e("aaa", "onDraw: " + x + "/" + y + "/" + weekDay + "/" + i + "/" + dayNum);
//            if (Integer.valueOf(dayNum) - 1 == clickData) {
                paint.setColor(color[i % 6]);
                canvas.drawRect(dayRect[Integer.valueOf(dayNum) - 1], paint);
//            }
            paint.setColor(Color.RED);
            if (day == Integer.valueOf(dayNum)) {
                paint.setColor(Color.BLACK);
            }
            canvas.drawText(dayNum, x, y, paint);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int wMode = MeasureSpec.getMode(widthMeasureSpec);
        int hMode = MeasureSpec.getMode(heightMeasureSpec);
        int w = MeasureSpec.getSize(widthMeasureSpec);
        int h = MeasureSpec.getSize(heightMeasureSpec);
        int width = w;
        int height = padding * 7;
        itemW = width / 7;
        setMeasuredDimension(wMode == MeasureSpec.EXACTLY ? w : width, hMode == MeasureSpec.EXACTLY ? h : height);
    }

    int startX;
    int endX;
    int startY;
    int endY;

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            startX = (int) event.getX();
            startY = (int) event.getY();
            Log.e("aaa", "dispatchTouchEvent: down - " + startX + "/" + startY);
        } else if (MotionEvent.ACTION_UP == event.getAction()) {
            endX = (int) event.getX();
            endY = (int) event.getY();
            Log.e("aaa", "dispatchTouchEvent: up - " + endX + "/" + endY);
        }
        return super.dispatchTouchEvent(event);
    }

    public void setMonth(int month) {
        this.month = month;
        invalidate();
    }
}

package com.project.viewtest.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.project.viewtest.R;
import com.project.viewtest.utils.SizeUtils;

/**
 * Created by Administrator on 2018/11/15.
 * 底部导航栏Item
 */

public class BottomView extends View {

    private Context context;
    private Bitmap icon;
    private String name;
    private Paint paint;
    private int iconToName;
    private final String TAG = BottomView.class.getSimpleName();
    private int iconSize;
    private int padding;
    private ColorMatrix green;

    public BottomView(Context context) {
        super(context);
        this.context = context;
        init(null, 0);
    }

    public BottomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init(attrs, 0);
    }

    public BottomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init(attrs, defStyleAttr);
    }

    private void init(AttributeSet attrs, int defStyleAttr) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.BottomView, defStyleAttr, 0);
        int iconRes = array.getResourceId(R.styleable.BottomView_icon, -1);
        name = array.getString(R.styleable.BottomView_name);
        array.recycle();
        iconSize = SizeUtils.dp2px(context, 30);
        if (iconRes == -1) {
            iconRes = R.drawable.icon5;
        }
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), iconRes);
        Matrix matrix = new Matrix();
        matrix.postScale(((float) iconSize / bitmap.getHeight()), ((float) iconSize / bitmap.getHeight()));
        icon = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        if (name == null) {
            name = "";
        }
        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(SizeUtils.sp2px(context, 10));
        green = new ColorMatrix(new float[]{
                0, 1, 0, 0, 0,
                1, 0, 0, 0, 0,
                0, 0, 1, 0, 0,
                0, 0, 0, 1, 0
        });
        paint.setColorFilter(new ColorMatrixColorFilter(green));
        iconToName = SizeUtils.dp2px(context, 1);
        padding = SizeUtils.dp2px(context, 5);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float left = getWidth() / 2 - icon.getWidth() / 2;
        canvas.drawBitmap(icon, left, padding, paint);
        Paint.FontMetricsInt fontMetricsInt = paint.getFontMetricsInt();
        float y = iconToName + iconSize - fontMetricsInt.top + padding;
        @SuppressLint("DrawAllocation") Rect rect = new Rect();
        paint.getTextBounds(name, 0, name.length(), rect);
        canvas.drawText(name, getWidth() / 2 - rect.width() / 2, y, paint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int measureWidth = MeasureSpec.getSize(widthMeasureSpec);
        int measureHeight = MeasureSpec.getSize(heightMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);
        Paint.FontMetricsInt fontMetricsInt = paint.getFontMetricsInt();
        int textHeight = fontMetricsInt.bottom - fontMetricsInt.top;
        int iconHeight = iconSize;
        int wrapHeight = textHeight + iconToName + iconHeight + padding * 2;
        setMeasuredDimension(measureWidth, modeHeight == MeasureSpec.AT_MOST ? wrapHeight : measureHeight);
    }

    public void changeColor(boolean flag) {
        if (flag) {
            setColor(0);
        } else {
            initColor();
        }
    }

    public void setColor(float f) {
        green.getArray()[0] = 1 - f;
        green.getArray()[1] = f;
        green.getArray()[5] = f;
        green.getArray()[6] = 1 - f;
        paint.setColorFilter(new ColorMatrixColorFilter(green));
        postInvalidate();
    }

    public void initColor(){
        green.getArray()[0] = 0;
        green.getArray()[1] = 1;
        green.getArray()[5] = 1;
        green.getArray()[6] = 0;
        paint.setColorFilter(new ColorMatrixColorFilter(green));
        postInvalidate();
    }
}

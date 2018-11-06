package com.project.viewtest.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2018/10/24.
 * 瀑布layout
 */

public class WaterFallLayout extends ViewGroup {

//    private Context context;
    private int[] bottom;
    private int count;
    private int verticalPadding;
    private int horizontalPadding;
    private OnItemClickListener listener;

    public WaterFallLayout(Context context) {
        this(context, null);
    }

    public WaterFallLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WaterFallLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
//        this.context = context;
        init();
    }

    private void init() {
        if (count == 0) {
            setCount(3);
        }
    }

    public void setHorizontalPadding(int horizontalPadding) {
        this.horizontalPadding = horizontalPadding;
    }

    public void setVerticalPadding(int verticalPadding) {
        this.verticalPadding = verticalPadding;
    }

    public void setPadding(int padding) {
        setHorizontalPadding(padding);
        setVerticalPadding(padding);
    }

    public void setCount(int count) {
        this.count = count;
        bottom = new int[count];
    }

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
        setItemListener();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int measureWidth = MeasureSpec.getSize(widthMeasureSpec);
        int measureHeight = MeasureSpec.getSize(heightMeasureSpec);
        int width = (measureWidth - (count + 1) * horizontalPadding) / count;
        int maxHeight;

        clearBottom();
        for (int i = 0; i < getChildCount(); i++) {
            final View view = getChildAt(i);
            measureChild(view, measureWidth, measureHeight);
            LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
            int min = getB();
            int height = view.getMeasuredHeight() * width / view.getMeasuredWidth();
            int vl = (min + 1) * horizontalPadding + min * width;
            int vr = vl + width;
            int vt = bottom[min] + verticalPadding;
            int vb = vt + height;
            layoutParams.left = vl;
            layoutParams.right = vr;
            layoutParams.top = vt;
            layoutParams.bottom = vb;
            bottom[min] = vb;
        }

//        clearBottom();
//        for (int i = 0; i < getChildCount(); i++) {
//            View view = getChildAt(i);
//            measureChild(view, measureWidth, measureHeight);
//            int height = view.getMeasuredHeight() * width / view.getMeasuredWidth();
//            int min = getB();
//            int vt = bottom[min] + verticalPadding;
//            int vb = vt + height;
//            bottom[min] = vb;
//        }
        maxHeight = getMax();
        setMeasuredDimension(measureWidth, maxHeight);
    }

    private int getMax() {
//        int max = 0;
        int maxB = 0;
        for (int i = 0; i < count; i++) {
            if (maxB < bottom[i]) {
//                max = i;
                maxB = bottom[i];
            }
        }
        return maxB;
    }

    @Override
    protected void onLayout(boolean changed, final int l, int t, int r, int b) {
//        int width = (getMeasuredWidth() - (count + 1) * horizontalPadding) / count;
        clearBottom();
        for (int i = 0; i < getChildCount(); i++) {
            final View view = getChildAt(i);

            LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
            view.layout(layoutParams.left, layoutParams.top, layoutParams.right, layoutParams.bottom);

//            int min = getB();
//            int height = view.getMeasuredHeight() * width / view.getMeasuredWidth();
//            int vl = (min + 1) * horizontalPadding + min * width;
//            int vr = vl + width;
//            int vt = bottom[min] + verticalPadding;
//            int vb = vt + height;
//            view.layout(vl, vt, vr, vb);
//            bottom[min] = vb;
        }
        setItemListener();
    }

    private void setItemListener() {
        for (int i = 0; i < getChildCount(); i++) {
            final View view = getChildAt(i);
            final int finalI = i;
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!=null){
                        listener.onItemClick(view, finalI);
                    }
                }
            });
        }
    }

    private void clearBottom() {
        for (int i = 0; i < bottom.length; i++) {
            bottom[i] = 0;
        }
    }

    private int getB() {
        int b = 0;
        int min = 100000;
        for (int i = 0; i < count; i++) {
//            Log.i("water", "getB: " + min + "/" + bottom[i]);
            if (min > bottom[i]) {
                min = bottom[i];
                b = i;
            }
        }
        return b;
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    @Override
    protected LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return new LayoutParams(p);
    }

    public static class LayoutParams extends ViewGroup.LayoutParams{

        public int left;
        public int right;
        public int top;
        public int bottom;

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }

        public LayoutParams(int width, int height) {
            super(width, height);
        }

        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
        }
    }

    public interface OnItemClickListener {

        void onItemClick(View view, int position);

    }
}

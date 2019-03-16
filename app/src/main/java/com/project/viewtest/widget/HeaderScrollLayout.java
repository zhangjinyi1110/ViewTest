package com.project.viewtest.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;

public class HeaderScrollLayout extends FrameLayout {

    private ScrollView scrollView;
    private LinearLayout linearLayout;
    private View headerView;
    private View contentView;

    private int start;
    private int scroll;

    public HeaderScrollLayout(Context context) {
        super(context);
        init();
    }

    public HeaderScrollLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HeaderScrollLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        scrollView = new ScrollView(getContext());
        linearLayout = new LinearLayout(getContext());
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        scrollView.addView(linearLayout);
        addView(scrollView);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            switch (i) {
                case 1:
                    headerView = view;
                    break;
                case 2:
                    contentView = view;
                    break;
                default:
                    removeAllViews();
                    addView(scrollView);
                    if (headerView != null) {
                        linearLayout.removeAllViews();
                        linearLayout.addView(headerView);
                    }
                    if (contentView != null) {
                        linearLayout.addView(contentView);
                    }
                    break;
            }
        }
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.getSize(heightMeasureSpec));
    }

}

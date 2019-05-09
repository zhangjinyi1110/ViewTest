package com.project.viewtest;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.project.viewtest.utils.SizeUtils;

public class CalendarDecoration extends RecyclerView.ItemDecoration {

    public int size = SizeUtils.dp2px(10);
    public int width;

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) view.getLayoutParams();
        layoutParams.setMargins(parent.getChildAdapterPosition(view) == 0 ? (int) (size * 3.5) : size, 0, size, 0);
        width = parent.getMeasuredWidth() - 7 * size;
        layoutParams.width = width;
        view.setLayoutParams(layoutParams);
    }
}

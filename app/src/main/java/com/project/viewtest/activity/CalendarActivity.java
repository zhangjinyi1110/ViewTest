package com.project.viewtest.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.project.viewtest.CalendarDecoration;
import com.project.viewtest.GalleryLayoutManager;
import com.project.viewtest.R;
import com.project.viewtest.adapter.SimpleAdapter;
import com.project.viewtest.databinding.ActivityCalendarBinding;
import com.project.viewtest.databinding.ItemCalendarViewBinding;
import com.project.viewtest.model.DateModel;

import java.util.ArrayList;
import java.util.List;

public class CalendarActivity extends AppCompatActivity {

    private int position;
    private CalendarDecoration itemDecoration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActivityCalendarBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_calendar);
//        binding.calendarRecycler.setLayoutManager(new GalleryLayoutManager());
        binding.calendarRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
        SimpleAdapter<DateModel, ItemCalendarViewBinding> adapter = new SimpleAdapter<DateModel, ItemCalendarViewBinding>(getApplicationContext()) {
            @Override
            protected int getLayoutId() {
                return R.layout.item_calendar_view;
            }

            @Override
            protected void convert(ItemCalendarViewBinding binding, DateModel dateModel, int position) {
                binding.item.setYear(dateModel.getYear());
                binding.item.setMonth(dateModel.getMonth());
            }
        };
        binding.calendarRecycler.setAdapter(adapter);
        List<DateModel> modelList = new ArrayList<>();
        for (int i = 2017; i < 2023; i++) {
            for (int j = 1; j < 13; j++) {
                DateModel dateModel = new DateModel(i, j);
                modelList.add(dateModel);
            }
        }
        adapter.setList(modelList);
        LinearSnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(binding.calendarRecycler);
        itemDecoration = new CalendarDecoration();
        binding.calendarRecycler.addItemDecoration(itemDecoration);
        binding.calendarRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            private int count;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int p = recyclerView.getChildCount();
                if (p == 0) {
                    Log.e("aaa", "onScrollStateChanged: p=0");
                    return;
                }
                if (newState == 1) {
                    position = ((RecyclerView.LayoutParams) recyclerView.getChildAt(p - 1)
                            .getLayoutParams()).getViewLayoutPosition() - 1;
                    Log.e("aaa", "onScrollStateChanged: " + p + "/" + position);
                    count = 0;
                } else if (newState == 0) {
                    position = ((RecyclerView.LayoutParams) recyclerView.getChildAt(p - 1)
                            .getLayoutParams()).getViewLayoutPosition() - 1;
                    set(recyclerView, position);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                count += dx;
//                Log.e("aaa", "onScrolled: " + dx + "/" + count);
                scroll(recyclerView, position, count);
            }
        });
    }

    private void set(RecyclerView recyclerView, int position) {
        View left = recyclerView.getLayoutManager().findViewByPosition(position - 1);
        View curr = recyclerView.getLayoutManager().findViewByPosition(position);
        View right = recyclerView.getLayoutManager().findViewByPosition(position + 1);
        float mAnimFactor = 0.8f;
        if (left != null) {
            left.setScaleY(mAnimFactor);
        }
        if (curr != null) {
            curr.setScaleY(1);
        }
        if (right != null) {
            right.setScaleY(mAnimFactor);
        }
    }

    private void scroll(RecyclerView recyclerView, int position, float count) {
        View left = recyclerView.getLayoutManager().findViewByPosition(position - 1);
        View curr = recyclerView.getLayoutManager().findViewByPosition(position);
        View right = recyclerView.getLayoutManager().findViewByPosition(position + 1);
        float mAnimFactor = 0.8f;
        if (count >= 0) {
            if (left != null) {
                float f = 1 - (1 - mAnimFactor) * (count / (itemDecoration.width + (itemDecoration.size * 2)));
                left.setScaleY(f);
//                left.setScaleY((1 - mAnimFactor) * percent + mAnimFactor);
            }
            if (curr != null) {
//                curr.setScaleY(curr.getMeasuredHeight() - curr.getMeasuredHeight() * (1 - mAnimFactor) * (count / (itemDecoration.width + (itemDecoration.size * 2))));
                //                mCurView.setScaleY(1 - percent * mAnimFactor);
                float f = 1 - (1 - mAnimFactor) * (count / (itemDecoration.width + (itemDecoration.size * 2)));
                curr.setScaleY(f);
                Log.e("aaa", "scroll: curr: " + f);
            }
            if (right != null) {
                float f = (1 - mAnimFactor) * (count / (itemDecoration.width + (itemDecoration.size * 2))) + mAnimFactor;
//                right.setScaleY(right.getMeasuredHeight() * mAnimFactor + right.getMeasuredHeight() * (1 - mAnimFactor));
                right.setScaleY(f);
//                mRightView.setScaleY((1 - mAnimFactor) + percent * mAnimFactor);
            }
        } else {
            count = Math.abs(count);
            if (left != null) {
                float f = (1 - mAnimFactor) * (count / (itemDecoration.width + (itemDecoration.size * 2))) + mAnimFactor;
                left.setScaleY(f);
//                left.setScaleY(1 - percent * mAnimFactor);
            }
            if (curr != null) {
                float f = 1 - (1 - mAnimFactor) * (count / (itemDecoration.width + (itemDecoration.size * 2)));
                curr.setScaleY(f);
//                curr.setScaleY((1 - mAnimFactor) + percent * mAnimFactor);
            }
            if (right != null) {
                float f = 1 - (1 - mAnimFactor) * (count / (itemDecoration.width + (itemDecoration.size * 2)));
                right.setScaleY(f);
//                right.setScaleY(1 - percent * mAnimFactor);
            }
        }
    }
}

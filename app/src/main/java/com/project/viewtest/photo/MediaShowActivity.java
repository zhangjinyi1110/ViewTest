package com.project.viewtest.photo;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.project.viewtest.R;
import com.project.viewtest.databinding.ActivityMediaShowBinding;

import java.util.ArrayList;
import java.util.List;

public class MediaShowActivity extends AppCompatActivity {

    private List<Photo> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMediaShowBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_media_show);
        list = getIntent().getParcelableArrayListExtra("photo");
        int curr = getIntent().getIntExtra("curr", 0);
        final PagerAdapter adapter = new PagerAdapter() {
            @Override
            public int getCount() {
                return list.size();
            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
                return o == view;
            }

            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup container, int position) {
                ImageView imageView = new ImageView(getApplicationContext());
                ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                imageView.setLayoutParams(layoutParams);
                Glide.with(getApplicationContext()).load(list.get(position).getPath()).into(imageView);
                container.addView(imageView);
                return imageView;
            }

            @Override
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                container.removeView((View) object);
            }
        };
        binding.pager.setAdapter(adapter);
        binding.pager.setCurrentItem(curr, false);
    }
}

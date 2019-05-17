package com.project.viewtest.photo;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.project.viewtest.R;
import com.project.viewtest.databinding.ActivityMediaShowBinding;

import java.util.List;

public class MediaShowActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMediaShowBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_media_show);
        final List<Photo> list = getIntent().getParcelableArrayListExtra("photo");
        PagerAdapter adapter = new PagerAdapter() {
            @Override
            public int getCount() {
                return list.size();
            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
                return o == view;
            }
        };
    }
}

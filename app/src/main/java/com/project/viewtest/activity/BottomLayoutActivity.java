package com.project.viewtest.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.project.viewtest.BottomFragment;
import com.project.viewtest.R;
import com.project.viewtest.widget.BottomLayout;

import java.util.ArrayList;
import java.util.List;

public class BottomLayoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_layout);
        FragmentManager manager = getSupportFragmentManager();
        final List<Fragment> list = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            list.add(BottomFragment.newInstance(i));
        }
        final ViewPager viewPager = findViewById(R.id.bottom_pager);
        viewPager.setAdapter(new FragmentPagerAdapter(manager) {
            @Override
            public Fragment getItem(int position) {
                return list.get(position);
            }

            @Override
            public int getCount() {
                return list.size();
            }
        });
        BottomLayout bottomLayout = findViewById(R.id.bottom_layout);
        bottomLayout.setViewPager(viewPager);
        bottomLayout.setChangeItemListener(new BottomLayout.OnChangeItemListener() {
            @Override
            public void onChange(View view, int position) {
                viewPager.setCurrentItem(position, false);
            }
        });
        bottomLayout.setCurr(0);
    }
}

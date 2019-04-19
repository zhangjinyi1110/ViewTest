package com.project.viewtest.activity;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.project.viewtest.R;
import com.project.viewtest.databinding.ActivityCalendarBinding;

public class CalendarActivity extends AppCompatActivity {

    private int month = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCalendarBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_calendar);
        binding.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        binding.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}

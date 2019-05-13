package com.project.viewtest.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.project.viewtest.R;
import com.project.viewtest.databinding.ActivityStatusBarBinding;
import com.project.viewtest.statusbar.BarHelper;

public class StatusBarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityStatusBarBinding barBinding = DataBindingUtil.setContentView(this, R.layout.activity_status_bar);
//        BarHelper.with(StatusBarActivity.this).setStatusBackgroundDrawable(getResources().getDrawable(R.drawable.bg));
        BarHelper.with(this)
                .hideActionBar()
                .setStatusBackgroundDrawable(getResources().getDrawable(R.drawable.icon4));
    }
}

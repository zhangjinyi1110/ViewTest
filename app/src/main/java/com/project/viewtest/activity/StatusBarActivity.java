package com.project.viewtest.activity;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.project.viewtest.R;
import com.project.viewtest.databinding.ActivityStatusBarBinding;
import com.project.viewtest.statusbar.StatusBar;

public class StatusBarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityStatusBarBinding barBinding = DataBindingUtil.setContentView(this, R.layout.activity_status_bar);
        StatusBar.with(StatusBarActivity.this).setBackgroundDrawable(getResources().getDrawable(R.drawable.bg));
    }
}

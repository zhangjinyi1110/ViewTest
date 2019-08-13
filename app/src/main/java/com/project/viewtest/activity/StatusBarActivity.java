package com.project.viewtest.activity;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

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
                .hideActionBar();
        barBinding.bjys.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BarHelper.with(StatusBarActivity.this).setStatusBackgroundColor(getResources().getColor(R.color.colorAccent));
            }
        });
        barBinding.bttp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BarHelper.with(StatusBarActivity.this).setStatusBackgroundDrawable(getResources().getDrawable(R.drawable.icon4));
            }
        });
        barBinding.tmztl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BarHelper.with(StatusBarActivity.this).hideHalfStatusBar();
            }
        });
        barBinding.ycztl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BarHelper.with(StatusBarActivity.this).hideStatusBar();
            }
        });
        barBinding.zths.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BarHelper.with(StatusBarActivity.this).lightColor();
            }
        });
        barBinding.ztbs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BarHelper.with(StatusBarActivity.this).darkColor();
            }
        });
    }
}

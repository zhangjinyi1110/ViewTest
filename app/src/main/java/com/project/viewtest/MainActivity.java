package com.project.viewtest;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.project.viewtest.activity.FlowActivity;
import com.project.viewtest.activity.LoadButtonActivity;
import com.project.viewtest.activity.LyricsActivity;
import com.project.viewtest.activity.RadialGradientActivity;
import com.project.viewtest.activity.SpringActivity;
import com.project.viewtest.activity.TestActivity;
import com.project.viewtest.activity.WaterFallActivity;
import com.project.viewtest.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        binding.mainFlowLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(FlowActivity.class);
            }
        });
        binding.mainWaterFallLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(WaterFallActivity.class);
            }
        });
        binding.mainTestLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(TestActivity.class);
            }
        });
        binding.mainSpringLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(SpringActivity.class);
            }
        });
        binding.mainRadialGradientView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(RadialGradientActivity.class);
            }
        });
        binding.mainLoadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(LoadButtonActivity.class);
            }
        });
        binding.mainLyricsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(LyricsActivity.class);
            }
        });
    }

    private void startActivity(Class aClass){
        startActivity(new Intent(this, aClass));
    }

}

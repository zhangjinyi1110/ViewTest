package com.project.viewtest.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.project.viewtest.R;
import com.project.viewtest.widget.WaterFallLayout;

import java.util.Random;

public class WaterFallActivity extends AppCompatActivity {

    int[] images = new int[]{R.drawable.icon1, R.drawable.icon2, R.drawable.icon6, R.drawable.icon4, R.drawable.icon5};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_fall);
        findViewById(R.id.water_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        final WaterFallLayout layout = findViewById(R.id.water_layout);
        layout.setPadding(15);
        findViewById(R.id.water_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView view = new ImageView(WaterFallActivity.this);
                view.setImageResource(getImage());
                layout.addView(view);
            }
        });
        layout.setListener(new WaterFallLayout.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getApplicationContext(), "第" + position + "个", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private int getImage() {
        return images[new Random().nextInt(images.length)];
    }
}

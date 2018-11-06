package com.project.viewtest.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.project.viewtest.R;
import com.project.viewtest.widget.FlowLayout;

public class FlowActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flow);
        final FlowLayout layout = findViewById(R.id.flow_layout);
        findViewById(R.id.flow_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        findViewById(R.id.flow_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView = new TextView(FlowActivity.this);
                textView.setText(getText());
                textView.setBackgroundResource(R.drawable.text_bg);
                ViewGroup.MarginLayoutParams layoutParams = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(15, 15, 0, 0);
                textView.setLayoutParams(layoutParams);
                layout.addView(textView);
            }
        });
    }

    private String getText() {
        int count = (int) ((Math.random() + Math.random()) * 10);
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < count; i++) {
            builder.append((char) ((int) (Math.random() * 93) + 32));
        }
        Log.i("flowActivity", "getText: " + count + "/" + builder.toString());
        return builder.toString();
    }
}

package com.project.viewtest.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.project.viewtest.R;
import com.project.viewtest.widget.LoadButton;

public class LoadButtonActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_button);
        final LoadButton button = findViewById(R.id.load_btn);
        findViewById(R.id.load_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        findViewById(R.id.load_failure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button.setResult(false);
            }
        });
        findViewById(R.id.load_success).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button.setResult(true);
            }
        });
        findViewById(R.id.load_reset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button.reset();
            }
        });
    }
}

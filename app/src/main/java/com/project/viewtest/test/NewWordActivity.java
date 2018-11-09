package com.project.viewtest.test;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.project.viewtest.R;

public class NewWordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_word);
        final EditText editText = findViewById(R.id.new_word_edit);
        findViewById(R.id.new_word_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(editText.getText())) {
                    String word = editText.getText().toString();
                    setResult(RESULT_OK, new Intent().putExtra("word", word));
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "请输入单词", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

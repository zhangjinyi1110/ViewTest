package com.project.viewtest.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import com.project.viewtest.MainActivity;
import com.project.viewtest.R;
import com.project.viewtest.databinding.ActivityTextSpanBinding;
import com.project.viewtest.textstyle.SpanHelper;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

public class TextSpanActivity extends AppCompatActivity {

    ActivityTextSpanBinding binding;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_text_span);
        TextView textView = findViewById(R.id.text);
        SpanHelper.simple("abcdefghijklmnopqrstuvwxyz")
                .fgSpan("ab", Color.YELLOW)
                .scriptSpan("cd", true)
                .bgSpan("ef", Color.RED)
                .scriptSpan(6, 8, false)
                .relSpan(8, 10, 3)
                .absSpan("klm", 20)
                .underLinkSpan("nop")
                .strikethroughSpan(16, 19)
                .customSpan("tuv", new ClickableSpan() {
                    @Override
                    public void onClick(@NonNull View widget) {
                        startActivity(new Intent(getApplicationContext(), TextSpanActivity.class));
                    }

                    @Override
                    public void updateDrawState(@NonNull TextPaint ds) {
                        ds.setUnderlineText(false);
                        ds.setColor(Color.BLUE);
                    }
                })
                .clickableSpan("wxyz", new ClickableSpan() {
                    @Override
                    public void onClick(@NonNull View widget) {
                        startActivity(new Intent(getApplicationContext(), TextSpanActivity.class));
                    }
                })
                .into(textView);
    }
}

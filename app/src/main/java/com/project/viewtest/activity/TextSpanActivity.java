package com.project.viewtest.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.text.style.TypefaceSpan;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.project.viewtest.MainActivity;
import com.project.viewtest.R;
import com.project.viewtest.databinding.ActivityTextSpanBinding;
import com.project.viewtest.textstyle.SpanHelper;
import com.project.viewtest.textstyle.SpanMultiple;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

public class TextSpanActivity extends AppCompatActivity {

    ActivityTextSpanBinding binding;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_text_span);
//        SpanHelper.simple("abcdefghijklmnopqrstuvwxyz")
//                .fgSpan("ab", Color.YELLOW)
//                .scriptSpan("cd", true)
//                .bgSpan("ef", Color.RED)
//                .scriptSpan(6, 8, false)
//                .relSpan(8, 10, 3)
//                .absSpan("klm", 20)
//                .underLinkSpan("nop")
//                .strikethroughSpan(16, 19)
//                .customSpan("tuv", new ClickableSpan() {
//                    @Override
//                    public void onClick(@NonNull View widget) {
//                        startActivity(new Intent(getApplicationContext(), TextSpanActivity.class));
//                    }
//
//                    @Override
//                    public void updateDrawState(@NonNull TextPaint ds) {
//                        ds.setUnderlineText(false);
//                        ds.setColor(Color.BLUE);
//                    }
//                })
//                .clickableSpan("wxyz", new ClickableSpan() {
//                    @Override
//                    public void onClick(@NonNull View widget) {
//                        startActivity(new Intent(getApplicationContext(), TextSpanActivity.class));
//                    }
//                })
//                .into(textView);

//        SpanHelper.multiple()
//                .bgSpan("abc", Color.BLUE)
//                .scriptSpan("de", true)
//                .fgSpan("fgh", Color.RED)
//                .scriptSpan("ij", false)
//                .absSpan("klm", 30)
//                .relSpan("nop", 2)
//                .underLinkSpan("qrst")
//                .clickableSpan("uvw", new ClickableSpan() {
//                    @Override
//                    public void onClick(@NonNull View widget) {
//                        Toast.makeText(getApplicationContext(), "toast", Toast.LENGTH_SHORT).show();
//                    }
//                })
//                .customSpan("xyz", new ClickableSpan() {
//                    @Override
//                    public void onClick(@NonNull View widget) {
//
//                    }
//
//                    @Override
//                    public void updateDrawState(@NonNull TextPaint ds) {
//                        ds.setUnderlineText(false);
//                    }
//                }).into(binding.text);

//        SpanMultiple multiple = SpanHelper.multiple();
//        for (int i = 0; i < 30; i++) {
//            final String str = i + "" + i + i;
//            final int finalI = i;
//            multiple.clickableSpan(str, new ClickableSpan() {
//                @Override
//                public void onClick(@NonNull View widget) {
//                    Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
//                }
//
//                @Override
//                public void updateDrawState(@NonNull TextPaint ds) {
//                    int f = finalI % 3;
//                    if (f == 0) {
//                        super.updateDrawState(ds);
//                    } else if (f == 1) {
//                        ds.setColor(Color.BLUE);
//                        ds.setUnderlineText(false);
//                    } else {
//                        ds.setColor(Color.RED);
//                    }
//                }
//            });
//        }
//        binding.text.setBackgroundColor(Color.WHITE);
//        multiple.into(binding.text);

        EditText editText = findViewById(R.id.edit);
        SpannableString spannableString = new SpannableString("五五五五五五");
        spannableString.setSpan(new StyleSpan(Typeface.NORMAL), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new StyleSpan(Typeface.BOLD_ITALIC), 1, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new StyleSpan(Typeface.BOLD), 2, 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new StyleSpan(Typeface.ITALIC), 3, 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        spannableString.setSpan(new StyleSpan(Typeface.ST), 4, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        editText.setText(spannableString);
    }
}

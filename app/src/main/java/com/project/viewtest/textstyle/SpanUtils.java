package com.project.viewtest.textstyle;

import android.support.annotation.NonNull;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class SpanUtils {

//    private List<SpannableString> stringList;

    private SpannableStringBuilder builder;

    private SpanUtils() {
//        stringList = new ArrayList<>();
        builder = new SpannableStringBuilder();
    }

    public static SpanUtils newInstance() {
        return new SpanUtils();
    }

    public SpanUtils clickSpan(CharSequence charSequence) {
        SpannableString spannableString = new SpannableString(charSequence);
        spannableString.setSpan(new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {

            }
        }, 0, charSequence.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.append(spannableString);
        return this;
    }

    public void into(TextView textView) {
        SpanHelper.newInstance()
                .range(2, 3)
                .clickSpan("hhhhh")
                .into(textView);
    }

}

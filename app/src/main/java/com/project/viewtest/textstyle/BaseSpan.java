package com.project.viewtest.textstyle;

import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

public class BaseSpan {

    SpannableStringBuilder builder;
    boolean isClick = false;

    BaseSpan() {
        this.builder = new SpannableStringBuilder();
    }

    public void into(TextView textView) {
        textView.setText(builder);
        if (isClick)
            textView.setMovementMethod(LinkMovementMethod.getInstance());
    }

}

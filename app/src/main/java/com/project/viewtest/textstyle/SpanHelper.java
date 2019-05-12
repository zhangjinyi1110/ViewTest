package com.project.viewtest.textstyle;

import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.widget.TextView;

public class SpanHelper implements Range {

    private SpanFactory factory;
    private SpannableStringBuilder builder;
    private int start = -1;
    private int end = -1;

    private SpanHelper() {
        factory = new SpanFactory();
        builder = new SpannableStringBuilder();
    }

    public static SpanHelper newInstance() {
        return new SpanHelper();
    }

    public SpanHelper clickSpan(CharSequence content) {
        SpannableString string = new SpannableString(content);
        int location[] = getLocation(content);
        string.setSpan(factory.getStyle(0), location[0],
                location[1], SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.append(string);
        return this;
    }

    private int[] getLocation(CharSequence content) {
        int start = this.start == -1 ? 0 : this.start;
        int end = this.end == -1 ? content.length() : this.end;
        this.start = -1;
        this.end = -1;
        return new int[]{start, end};
    }

    @Override
    public SpanHelper range(int start, int end) {
        this.start = start;
        this.end = end;
        return this;
    }

    public void into(TextView textView) {
        textView.setText(builder);
    }
}

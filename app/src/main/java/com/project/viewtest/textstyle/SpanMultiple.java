package com.project.viewtest.textstyle;

import android.support.annotation.ColorInt;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.SubscriptSpan;
import android.text.style.SuperscriptSpan;
import android.text.style.TypefaceSpan;
import android.text.style.UnderlineSpan;

public class SpanMultiple extends BaseSpan {

    //字体(default,default-bold,monospace,serif,sans-serif)
    public SpanMultiple typefaceSpan(String[] contents, String type) {
        builder.append(getSpan(new TypefaceSpan(type), contents));
        return this;
    }

    //字体大小，绝对值
    public SpanMultiple absSpan(String[] contents, int size) {
        builder.append(getSpan(new AbsoluteSizeSpan(size), contents));
        return this;
    }

    //字体大小，相对值
    public SpanMultiple relSpan(String[] contents, float pro) {
        builder.append(getSpan(new RelativeSizeSpan(pro), contents));
        return this;
    }

    //前景颜色
    public SpanMultiple fgSpan(String[] contents, @ColorInt int color) {
        builder.append(getSpan(new ForegroundColorSpan(color), contents));
        return this;
    }

    //背景颜色
    public SpanMultiple bgSpan(String[] contents, @ColorInt int color) {
        builder.append(getSpan(new BackgroundColorSpan(color), contents));
        return this;
    }

    //字体样式: NORMAL正常，BOLD粗体，ITALIC斜体，BOLD_ITALIC粗斜体
    public SpanMultiple styleSpan(String[] contents, int style) {
        builder.append(getSpan(new StyleSpan(style), contents));
        return this;
    }

    //下划线
    public SpanMultiple underLinkSpan(String[] contents) {
        builder.append(getSpan(new UnderlineSpan(), contents));
        return this;
    }

    //删除线
    public SpanMultiple strikethroughSpan(String[] contents) {
        builder.append(getSpan(new StrikethroughSpan(), contents));
        return this;
    }

    //上下标
    public SpanMultiple scriptSpan(String[] contents, boolean up) {
        builder.append(getSpan(up ? new SuperscriptSpan() : new SubscriptSpan(), contents));
        return this;
    }

    //点击事件
    public SpanMultiple clickableSpan(String[] contents, ClickableSpan clickableSpan) {
        builder.append(getSpan(clickableSpan, contents));
        isClick = true;
        return this;
    }

    //自定义
    public SpanMultiple customSpan(String[] contents, Object span) {
        builder.append(getSpan(span, contents));
        if (span instanceof ClickableSpan)
            isClick = true;
        return this;
    }

    private CharSequence getSpan(Object span, String[] contents) {
        SpannableStringBuilder builder = new SpannableStringBuilder();
        for (String content : contents) {
            SpannableString spannableString = new SpannableString(content);
            spannableString.setSpan(span, 0, content.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            builder.append(spannableString);
        }
        return builder;
    }

}

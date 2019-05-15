package com.project.viewtest.textstyle;

import android.support.annotation.ColorInt;
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

public class SpanSimple extends BaseSpan {

    private String content;

    SpanSimple(String content) {
        super();
        builder.append(content);
        this.content = content;
    }

    //字体(default,default-bold,monospace,serif,sans-serif)
    public SpanSimple typefaceSpan(int start, int end, String type) {
        setSpan(new TypefaceSpan(type), start, end);
        return this;
    }

    public SpanSimple typefaceSpan(String match, String type) {
        setSpan(new TypefaceSpan(type), match);
        return this;
    }

    //字体大小，绝对值
    public SpanSimple absSpan(int start, int end, int size) {
        setSpan(new AbsoluteSizeSpan(size), start, end);
        return this;
    }
    public SpanSimple absSpan(String match, int size) {
        setSpan(new AbsoluteSizeSpan(size), match);
        return this;
    }

    //字体大小，相对值
    public SpanSimple relSpan(int start, int end, float pro) {
        setSpan(new RelativeSizeSpan(pro), start, end);
        return this;
    }

    public SpanSimple relSpan(String match, float pro) {
        setSpan(new RelativeSizeSpan(pro), match);
        return this;
    }

    //前景颜色
    public SpanSimple fgSpan(int start, int end, @ColorInt int color) {
        setSpan(new ForegroundColorSpan(color), start, end);
        return this;
    }

    public SpanSimple fgSpan(String match, @ColorInt int color) {
        setSpan(new ForegroundColorSpan(color), match);
        return this;
    }

    //背景颜色
    public SpanSimple bgSpan(int start, int end, @ColorInt int color) {
        setSpan(new BackgroundColorSpan(color), start, end);
        return this;
    }

    public SpanSimple bgSpan(String match, @ColorInt int color) {
        setSpan(new BackgroundColorSpan(color), match);
        return this;
    }

    //字体样式: NORMAL正常，BOLD粗体，ITALIC斜体，BOLD_ITALIC粗斜体
    public SpanSimple styleSpan(int start, int end, int style) {
        setSpan(new StyleSpan(style), start, end);
        return this;
    }

    public SpanSimple styleSpan(String match, int style) {
        setSpan(new StyleSpan(style), match);
        return this;
    }

    //下划线
    public SpanSimple underLinkSpan(int start, int end) {
        setSpan(new UnderlineSpan(), start, end);
        return this;
    }

    public SpanSimple underLinkSpan(String match) {
        setSpan(new UnderlineSpan(), match);
        return this;
    }

    //删除线
    public SpanSimple strikethroughSpan(int start, int end) {
        setSpan(new StrikethroughSpan(), start, end);
        return this;
    }

    public SpanSimple strikethroughSpan(String match) {
        setSpan(new StrikethroughSpan(), match);
        return this;
    }

    //上下标
    public SpanSimple scriptSpan(int start, int end, boolean up) {
        setSpan(up ? new SuperscriptSpan() : new SubscriptSpan(), start, end);
        return this;
    }

    public SpanSimple scriptSpan(String match, boolean up) {
        setSpan(up ? new SuperscriptSpan() : new SubscriptSpan(), match);
        return this;
    }

    //点击事件
    public SpanSimple clickableSpan(int start, int end, ClickableSpan clickableSpan) {
        setSpan(clickableSpan, start, end);
        isClick = true;
        return this;
    }

    public SpanSimple clickableSpan(String match, ClickableSpan clickableSpan) {
        setSpan(clickableSpan, match);
        isClick = true;
        return this;
    }

    //自定义
    public SpanSimple customSpan(int start, int end, Object span) {
        setSpan(span, start, end);
        return this;
    }

    public SpanSimple customSpan(String match, Object span) {
        setSpan(span, match);
        return this;
    }

    private void setSpan(Object span, int start, int end) {
        builder.setSpan(span, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    private void setSpan(Object span, String match) {
        int start = content.indexOf(match);
        if (start != -1)
            builder.setSpan(span, start, start + match.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

}

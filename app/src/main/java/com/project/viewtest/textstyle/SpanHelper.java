package com.project.viewtest.textstyle;

public class SpanHelper {

    public static SpanSimple simple(String content) {
        return new SpanSimple(content);
    }

    public static SpanMultiple multiple() {
        return new SpanMultiple();
    }

}

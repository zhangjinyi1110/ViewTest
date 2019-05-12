package com.project.viewtest.textstyle;

import android.support.annotation.NonNull;
import android.text.style.CharacterStyle;
import android.text.style.ClickableSpan;
import android.view.View;

public class SpanFactory {

    CharacterStyle getStyle(int type) {
        CharacterStyle style = null;
        switch (type) {
            case 0:
                style = new ClickableSpan() {
                    @Override
                    public void onClick(@NonNull View widget) {

                    }
                };
                break;
        }
        return style;
    }

}

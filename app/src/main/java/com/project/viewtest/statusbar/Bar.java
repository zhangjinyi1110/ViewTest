package com.project.viewtest.statusbar;

import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;

public interface Bar {

    Bar setStatusBackgroundColor(@ColorInt int color);

    Bar setStatusBackgroundDrawable(Drawable drawable);

    Bar hideActionBar();

    Bar hideStatusBar();

    Bar hideHalfStatusBar();

    Bar lightColor();

    Bar darkColor();

    void build();

}

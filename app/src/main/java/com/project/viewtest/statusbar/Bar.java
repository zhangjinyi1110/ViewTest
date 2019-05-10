package com.project.viewtest.statusbar;

import android.graphics.drawable.Drawable;

public interface Bar {

    Bar setBackgroundColor(int color);

    Bar setBackgroundDrawable(Drawable drawable);

    Bar hideActionBar();

    Bar hideStatusBar();

    Bar hideHalfStatusBar();

}

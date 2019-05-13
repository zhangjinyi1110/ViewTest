package com.project.viewtest.statusbar;

import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;

public interface StatusBar {

    //设置状态栏背景颜色
    StatusBar setStatusBackgroundColor(@ColorInt int color);

    //设置状态栏背景图片
    StatusBar setStatusBackgroundDrawable(Drawable drawable);

    //隐藏标题栏
    StatusBar hideActionBar();

    //隐藏状态栏
    StatusBar hideStatusBar();

    //隐藏状态栏但显示文字图标
    StatusBar hideHalfStatusBar();

    //亮色主题时字体为黑色
    StatusBar lightColor();

    //暗色主题时字体为白色
    StatusBar darkColor();

}

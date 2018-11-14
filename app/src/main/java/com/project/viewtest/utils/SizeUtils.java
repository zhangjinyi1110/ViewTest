package com.project.viewtest.utils;

import android.content.Context;

/**
 * Created by Administrator on 2018/11/13.
 * sizeUtils
 */

public class SizeUtils {

    public static int dp2px(Context context, float value){
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (scale * value);
    }

}

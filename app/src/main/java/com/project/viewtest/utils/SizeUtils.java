package com.project.viewtest.utils;

import android.content.Context;
import android.content.res.Resources;

/**
 * Created by Administrator on 2018/11/13.
 * sizeUtils
 */

public class SizeUtils {

    public static int dp2px(Context context, float value){
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (scale * value);
    }

    public static int sp2px(Context context, float value){
        float scale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (scale * value);
    }

    public static int dp2px(float value){
        float scale = Resources.getSystem().getDisplayMetrics().density;//context.getResources().getDisplayMetrics().density;
        return (int) (scale * value);
    }

}

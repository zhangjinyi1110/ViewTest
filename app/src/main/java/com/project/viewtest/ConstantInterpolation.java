package com.project.viewtest;

import android.animation.TimeInterpolator;

/**
 * Created by Administrator on 2018/11/14.
 * 匀速
 */

public class ConstantInterpolation implements TimeInterpolator {
    @Override
    public float getInterpolation(float input) {
        return input;
    }
}

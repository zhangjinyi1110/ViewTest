package com.project.viewtest.photo;

import android.animation.ValueAnimator;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.DrawableImageViewTarget;
import com.project.viewtest.R;
import com.project.viewtest.databinding.ActivityShowPhotoBinding;
import com.project.viewtest.statusbar.BarHelper;

public class ShowPhotoActivity extends FragmentActivity {

    private ActivityShowPhotoBinding binding;
    private int width;
    private int height;
    private int left;
    private int top;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_show_photo);
        left = getIntent().getIntExtra("left", 0);
        top = getIntent().getIntExtra("top", 0);
        width = getIntent().getIntExtra("width", 0);
        height = getIntent().getIntExtra("height", 0);
        binding.image.setX(left);
        binding.image.setY(top);
        ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(width, height);
        binding.image.setLayoutParams(layoutParams);
        Glide.with(this).load(getIntent().getStringExtra("url")).into(new DrawableImageViewTarget(binding.image) {
            @Override
            protected void setResource(@Nullable Drawable resource) {
                super.setResource(resource);
//                show();
            }
        });
    }

    private void show() {
        final float ww = getResources().getDisplayMetrics().widthPixels;
        final float wh = getResources().getDisplayMetrics().heightPixels;
        float w = width / ww;
        float h = height / wh;
        ValueAnimator animator = ValueAnimator.ofFloat(Math.max(w, h), 1);
        animator.setDuration(3000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float f = (float) animation.getAnimatedValue();
                int w = (int) (ww * f);
                int h = (int) (wh * f);
                ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(w, h);
                binding.image.setLayoutParams(layoutParams);
                Log.e("aaa", "onAnimationUpdate: " + w + "/" + h);
                if (left >= ww / 2) {

                } else {
                    if (ww - left == width / 2) {

                    } else {

                    }
                }
            }
        });
        animator.start();
    }
}

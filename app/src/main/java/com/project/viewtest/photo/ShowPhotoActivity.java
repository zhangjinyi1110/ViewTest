package com.project.viewtest.photo;

import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.project.viewtest.R;
import com.project.viewtest.databinding.ActivityShowPhotoBinding;

public class ShowPhotoActivity extends FragmentActivity {

    private ActivityShowPhotoBinding binding;
    private int intentWidth;
    private int intentHeight;
    private int intentLeft;
    private int intentTop;
    private int mLeftDelta;
    private int mTopDelta;
    private float mWidthScale;
    private float mHeightScale;

    private LinearLayout linearLayout;
    private ColorDrawable colorDrawable;
    private long DURATION = 150;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_show_photo);
        intentLeft = getIntent().getIntExtra("left", 0);
        intentTop = getIntent().getIntExtra("top", 0);
        intentWidth = getIntent().getIntExtra("width", 0);
        intentHeight = getIntent().getIntExtra("height", 0);
//        binding.image.setX(intentLeft);
//        binding.image.setY(intentTop);
//        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(intentWidth, intentHeight);
//        binding.image.setLayoutParams(layoutParams);
        Glide.with(this).load(getIntent().getStringExtra("url")).into(binding.image);
        linearLayout = findViewById(R.id.linear_bg);
        colorDrawable = new ColorDrawable(Color.BLACK);
        //布局背景设置
        linearLayout.setBackgroundDrawable(colorDrawable);
        if (savedInstanceState == null) {
            ViewTreeObserver observer = binding.image.getViewTreeObserver();
            observer.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    binding.image.getViewTreeObserver().removeOnPreDrawListener(this);
                    //坐标的获取设置
                    int[] screenLocation = new int[2];
                    binding.image.getLocationOnScreen(screenLocation);
                    mLeftDelta = intentLeft - screenLocation[0];
                    mTopDelta = intentTop - screenLocation[1];

                    mWidthScale = (float) intentWidth / binding.image.getWidth();
                    mHeightScale = (float) intentHeight / binding.image.getHeight();
                    //开启缩放动画
                    enterAnimation();

                    return true;
                }
            });
        }

        binding.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exitAnimation(new Runnable() {
                    public void run() {
                        finish();
                        //取消activity动画
                        overridePendingTransition(0, 0);
                    }
                });
            }
        });
    }

    @Override
    public void onBackPressed() {
        exitAnimation(new Runnable() {
            public void run() {
                finish();
                //取消activity动画
                overridePendingTransition(0, 0);
            }
        });
    }

    //进入动画
    public void enterAnimation() {
        //设置imageview动画的初始值
        binding.image.setPivotX(0);
        binding.image.setPivotY(0);
        binding.image.setScaleX(mWidthScale);
        binding.image.setScaleY(mHeightScale);
        binding.image.setTranslationX(mLeftDelta);
        binding.image.setTranslationY(mTopDelta);
        //设置动画
        TimeInterpolator sDecelerator = new DecelerateInterpolator();
        //设置imageview缩放动画，以及缩放开始位置
        binding.image.animate().setDuration(DURATION).scaleX(1).scaleY(1).
                translationX(0).translationY(0).setInterpolator(sDecelerator);

        // 设置activity主布局背景颜色DURATION毫秒内透明度从透明到不透明
        ObjectAnimator bgAnim = ObjectAnimator.ofInt(colorDrawable, "alpha", 0, 255);
        bgAnim.setDuration(DURATION);
        bgAnim.start();
    }

    public void exitAnimation(final Runnable endAction) {

        TimeInterpolator sInterpolator = new AccelerateInterpolator();
        //设置imageview缩放动画，以及缩放结束位置
        binding.image.animate().setDuration(DURATION).scaleX(mWidthScale).scaleY(mHeightScale).
                translationX(mLeftDelta).translationY(mTopDelta)
                .setInterpolator(sInterpolator).withEndAction(endAction);

        // 设置activity主布局背景颜色DURATION毫秒内透明度从不透明到透明
        ObjectAnimator bgAnim = ObjectAnimator.ofInt(colorDrawable, "alpha", 0);
        bgAnim.setDuration(DURATION);
        bgAnim.start();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }
}

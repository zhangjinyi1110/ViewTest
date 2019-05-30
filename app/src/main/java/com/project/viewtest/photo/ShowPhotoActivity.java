package com.project.viewtest.photo;

import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.DrawableImageViewTarget;
import com.project.viewtest.R;
import com.project.viewtest.databinding.ActivityShowPhotoBinding;

public class ShowPhotoActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActivityShowPhotoBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_show_photo);
        int left = getIntent().getIntExtra("left", 0);
        int top = getIntent().getIntExtra("top", 0);
        int width = getIntent().getIntExtra("width", 0);
        int height = getIntent().getIntExtra("height", 0);
        binding.image.setX(left);
        binding.image.setY(top);
        ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(width, height);
        binding.image.setLayoutParams(layoutParams);
        Glide.with(this).load(getIntent().getStringExtra("url")).into(new DrawableImageViewTarget(binding.image) {
            @Override
            protected void setResource(@Nullable Drawable resource) {
                super.setResource(resource);
                binding.image.setScaleX(3);
                binding.image.setScaleY(3);
            }
        });
    }
}

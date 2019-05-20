package com.project.viewtest.photo;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import com.bumptech.glide.Glide;
import com.project.viewtest.R;
import com.project.viewtest.adapter.SimpleAdapter;
import com.project.viewtest.databinding.ActivitySelectMediaBinding;
import com.project.viewtest.databinding.ItemSelectMediaBinding;

import java.util.ArrayList;
import java.util.List;

public class SelectMediaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySelectMediaBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_select_media);
        final ArrayList<Photo> list = MediaHelper.queryPhoto(this).queryAll();
        SimpleAdapter<Photo, ItemSelectMediaBinding> adapter = new SimpleAdapter<Photo, ItemSelectMediaBinding>(getApplicationContext()) {
            @Override
            protected int getLayoutId() {
                return R.layout.item_select_media;
            }

            @Override
            protected void convert(ItemSelectMediaBinding binding, Photo s, final int position) {
                Glide.with(getApplicationContext())
                        .load(s.getPath())
                        .into(binding.image);
                binding.image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getApplicationContext(), MediaShowActivity.class)
                                .putParcelableArrayListExtra("photo", list)
                                .putExtra("curr", position));
                    }
                });
            }
        };
        binding.mediaRecycler.setLayoutManager(new GridLayoutManager(getApplicationContext(), 4));
        binding.mediaRecycler.setAdapter(adapter);
        adapter.addList(list);
    }
}

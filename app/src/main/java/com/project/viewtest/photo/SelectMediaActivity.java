package com.project.viewtest.photo;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import com.bumptech.glide.Glide;
import com.project.viewtest.R;
import com.project.viewtest.adapter.SimpleAdapter;
import com.project.viewtest.databinding.ActivitySelectMediaBinding;
import com.project.viewtest.databinding.ItemSelectMediaBinding;

import java.util.ArrayList;

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
            protected void convert(final ItemSelectMediaBinding binding, final Photo s, final int position) {
                Glide.with(getApplicationContext())
                        .load(s.getPath())
                        .into(binding.image);
                ViewCompat.setTransitionName(binding.image, s.getPath());
                binding.image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        startActivity(new Intent(getApplicationContext(), MediaShowActivity.class)
//                                .putParcelableArrayListExtra("photo", list)
//                                .putExtra("curr", position));
                        PhotoDialogFragment dialogFragment = PhotoDialogFragment.newInstance(s.getPath());
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction()
                                .addSharedElement(binding.image, "TransitionName");
                        dialogFragment.show(transaction, s.getPath());
                    }
                });
            }
        };
        binding.mediaRecycler.setLayoutManager(new GridLayoutManager(getApplicationContext(), 4));
        binding.mediaRecycler.setAdapter(adapter);
        adapter.addList(list);
    }
}

package com.project.viewtest.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.project.viewtest.R;
import com.project.viewtest.databinding.ActivityScrollBinding;
import com.project.viewtest.widget.ScrollView;

public class ScrollActivity extends AppCompatActivity {

    private ActivityScrollBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_scroll);
//        binding.scroll.setShowCancel(false);
//        ImageView imageView = new ImageView(this);
//        imageView.setImageResource(R.mipmap.ic_launcher);
//        binding.scroll.setOk(imageView);
        binding.scroll.setItemClickListener(new ScrollView.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int id) {
                switch (id) {
                    case ScrollView.TYPE_CANCEL:
                        binding.scroll.setBtnClose(false);
                        Toast.makeText(ScrollActivity.this, "cancel", Toast.LENGTH_SHORT).show();
                        break;
                    case ScrollView.TYPE_MORE:
                        Toast.makeText(ScrollActivity.this, "more", Toast.LENGTH_SHORT).show();
                        break;
                    case ScrollView.TYPE_OK:
                        binding.scroll.setBtnClose(true);
                        Toast.makeText(ScrollActivity.this, "ok", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
    }
}

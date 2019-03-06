package com.project.viewtest.activity;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.project.viewtest.R;
import com.project.viewtest.adapter.SimpleAdapter;
import com.project.viewtest.databinding.ActivityHeaderLayoutBinding;
import com.project.viewtest.databinding.ItemSpringBinding;
import com.project.viewtest.databinding.ItemWordBinding;

import java.util.ArrayList;
import java.util.List;

public class HeaderLayoutActivity extends AppCompatActivity {

    private ActivityHeaderLayoutBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_header_layout);
        SimpleAdapter<String, ItemSpringBinding> adapter = new SimpleAdapter<String, ItemSpringBinding>(this) {
            @Override
            protected int getLayoutId() {
                return R.layout.item_spring;
            }

            @Override
            protected void convert(ItemSpringBinding binding, String s, int position) {
                binding.springMessage.setText(s);
            }
        };
        binding.recycler.setLayoutManager(new LinearLayoutManager(this));
        binding.recycler.setAdapter(adapter);
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            list.add("listSize:" + (i + 1));
        }
        adapter.setList(list);
    }
}

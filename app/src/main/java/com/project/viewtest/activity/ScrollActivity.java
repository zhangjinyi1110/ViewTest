package com.project.viewtest.activity;

import android.databinding.DataBindingUtil;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.project.viewtest.R;
import com.project.viewtest.adapter.SimpleAdapter;
import com.project.viewtest.databinding.ActivityScrollBinding;
import com.project.viewtest.databinding.ItemScrollBinding;
import com.project.viewtest.utils.SizeUtils;
import com.project.viewtest.widget.ScrollView;

import java.util.ArrayList;
import java.util.List;

public class ScrollActivity extends AppCompatActivity {

    private ActivityScrollBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_scroll);
        SimpleAdapter<String, ItemScrollBinding> adapter = new SimpleAdapter<String, ItemScrollBinding>(this) {
            @Override
            protected int getLayoutId() {
                return R.layout.item_scroll;
            }

            @Override
            protected void convert(final ItemScrollBinding binding, final String s, int position) {
                binding.itemName.setText(s);
                binding.scrollView.setItemClickListener(new ScrollView.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int id) {
                        switch (id) {
                            case ScrollView.TYPE_OK:
                                binding.scrollView.setBtnClose(true);
                                break;
                            case ScrollView.TYPE_CANCEL:
                                binding.scrollView.setBtnClose(false);
                                break;
                            case ScrollView.TYPE_MORE:
                                if (binding.scrollView.isOpenBtn()) {
                                    binding.scrollView.closeBtn();
                                }
                                break;
                        }
                    }
                });
                binding.itemName.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                    }
                });
                binding.itemImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(), s + "/image", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        };
        binding.scrollRecycler.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                if (parent.getChildLayoutPosition(view) != 0) {
                    outRect.top = SizeUtils.dp2px(getApplicationContext(), 1);
                }
            }
        });
        binding.scrollRecycler.setLayoutManager(new LinearLayoutManager(this));
        binding.scrollRecycler.setAdapter(adapter);
        List<String> strings = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            strings.add("string" + i);
        }
        adapter.addList(strings);
    }
}

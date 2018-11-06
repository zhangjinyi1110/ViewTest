package com.project.viewtest.activity;

import android.annotation.SuppressLint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.project.viewtest.R;
import com.project.viewtest.adapter.SimpleAdapter;
import com.project.viewtest.databinding.ItemSpringBinding;
import com.project.viewtest.model.MessageModel;
import com.project.viewtest.widget.SpringView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.subjects.PublishSubject;

public class SpringActivity extends AppCompatActivity {

    private List<MessageModel> list;
    private SimpleAdapter<MessageModel, ItemSpringBinding> adapter;
    private ViewGroup parent;
    private PublishSubject<Boolean> subject;
    //    private boolean isMove = false;
    private SpringView springView;
    private int[] p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spring);
        parent = findViewById(R.id.spring_parent);
        springView = findViewById(R.id.spring_view);
        initList();
        adapter = new SimpleAdapter<MessageModel, ItemSpringBinding>(list, this) {
            @Override
            protected int getLayoutId() {
                return R.layout.item_spring;
            }

            @Override
            protected void convert(ItemSpringBinding binding, final MessageModel messageModel, int position) {
                binding.springName.setText(messageModel.getName());
                binding.springMessage.setText(messageModel.getMessage());
                if (messageModel.getNotRead().equals("0")) {
                    binding.springView.setVisibility(View.INVISIBLE);
                    return;
                } else {
                    binding.springView.setVisibility(View.VISIBLE);
                }
                binding.springView.setText(messageModel.getNotRead());
                binding.springView.setOnTouchListener(new View.OnTouchListener() {
                    @SuppressLint("ClickableViewAccessibility")
                    @Override
                    public boolean onTouch(final View v, MotionEvent event) {
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            check(v, event, messageModel.getNotRead());
                            springView.setListener(new SpringView.OnOffListener() {
                                @Override
                                public void onOff() {
//                                    v.setVisibility(View.VISIBLE);
                                    messageModel.setNotRead("0");
                                }

                                @Override
                                public void onOn() {
                                    v.setVisibility(View.VISIBLE);
                                }
                            });
                        }
                        return false;
                    }
                });
            }
        };
        RecyclerView recyclerView = findViewById(R.id.spring_recycler);
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.bottom = 3;
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void check(final View v, MotionEvent event, String num) {
        int[] lt = new int[2];
        v.getLocationInWindow(lt);
        int l = lt[0];
        int r = l + v.getWidth();
        int t = lt[1];
        int b = t + v.getHeight();
        float x = event.getRawX();
        float y = event.getRawY();
        boolean flag = l <= x && x <= r && t <= y && y <= b;
        if (flag) {
            v.setVisibility(View.INVISIBLE);
            if (p == null) {
                p = new int[2];
                parent.getLocationInWindow(p);
            }
            springView.setText(num);
            event.setLocation((l + r) / 2, (t + b - 2 * p[1]) / 2);
            springView.actionDown(event);
        }
    }

    private void initList() {
        list = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            list.add(new MessageModel(("小明" + i), ("消息" + i), String.valueOf(i)));
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_MOVE && springView.getVisibility() == View.VISIBLE) {
            ev.setLocation(ev.getX(), ev.getY() - p[1]);
            springView.actionMove(ev);
            return true;
        } else if (ev.getAction() == MotionEvent.ACTION_UP && springView.getVisibility() == View.VISIBLE) {
            springView.actionUp();
            return true;
        }
        return super.dispatchTouchEvent(ev);
    }
}

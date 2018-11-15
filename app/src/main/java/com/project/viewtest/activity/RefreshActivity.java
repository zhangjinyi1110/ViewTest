package com.project.viewtest.activity;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.project.viewtest.R;
import com.project.viewtest.adapter.SimpleAdapter;
import com.project.viewtest.databinding.ItemWordBinding;
import com.project.viewtest.test.Word;
import com.project.viewtest.utils.SizeUtils;
import com.project.viewtest.widget.RefreshLayout;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class RefreshActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refresh);
        final List<Word> list = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            list.add(new Word("list" + i));
        }
        final RefreshLayout refreshLayout = findViewById(R.id.refresh_view);
        final RecyclerView recyclerView = findViewById(R.id.refresh_recycler);
        final SimpleAdapter<Word, ItemWordBinding> adapter = new SimpleAdapter<Word, ItemWordBinding>(this) {
            @Override
            protected int getLayoutId() {
                return R.layout.item_word;
            }

            @Override
            protected void convert(ItemWordBinding binding, Word word, int position) {
                binding.setWrod(word);
                binding.getRoot().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        refreshLayout.setRefresh(true);
                    }
                });
            }
        };
        adapter.setList(list);
        refreshLayout.setOnRefreshListener(new RefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                final int sum = list.size();
                Observable.create(new ObservableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                        Thread.sleep(2000);
                        e.onNext(0);
                        e.onNext(1);
                        e.onNext(2);
                        e.onNext(3);
                        e.onNext(4);
                        e.onComplete();
                    }
                }).subscribeOn(Schedulers.computation())
                        .map(new Function<Integer, Word>() {
                            @Override
                            public Word apply(Integer integer) throws Exception {
                                return new Word("new list" + (sum + integer));
                            }
                        }).subscribe(new Consumer<Word>() {
                                         @Override
                                         public void accept(Word word) throws Exception {
                                             list.add(word);
                                         }
                                     }
                        , new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                        , new Action() {
                            @Override
                            public void run() throws Exception {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        adapter.setList(list);
                                        refreshLayout.setRefresh(false);
                                    }
                                });
                            }
                        });
            }
        });
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.bottom = SizeUtils.dp2px(RefreshActivity.this, 1);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
}

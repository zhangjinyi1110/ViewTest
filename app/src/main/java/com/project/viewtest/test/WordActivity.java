package com.project.viewtest.test;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.project.viewtest.R;
import com.project.viewtest.adapter.SimpleAdapter;
import com.project.viewtest.databinding.ActivityWordBinding;
import com.project.viewtest.databinding.ItemWordBinding;

import java.util.List;

public class WordActivity extends AppCompatActivity {

    private WordViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityWordBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_word);
        final SimpleAdapter<Word, ItemWordBinding> adapter = new SimpleAdapter<Word, ItemWordBinding>(this) {
            @Override
            protected int getLayoutId() {
                return R.layout.item_word;
            }

            @Override
            protected void convert(ItemWordBinding binding, final Word word, int position) {
                binding.setWrod(word);
                binding.getRoot().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viewModel.delete(word);
                    }
                });
            }
        };
        binding.wordRecycler.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.bottom = 3;
            }
        });
        binding.wordRecycler.setLayoutManager(new LinearLayoutManager(this));
        binding.wordRecycler.setAdapter(adapter);
        viewModel = ViewModelProviders.of(this).get(WordViewModel.class);
        viewModel.getLiveData().observe(this, new Observer<List<Word>>() {
            @Override
            public void onChanged(@Nullable List<Word> words) {
                adapter.setList(words);
            }
        });
        binding.wordFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(WordActivity.this, NewWordActivity.class), 0x00);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==0x00 && resultCode==RESULT_OK){
            if(data!=null){
                Word word = new Word();
                word.setWord(data.getStringExtra("word"));
                viewModel.insert(word);
            }
        }
    }
}

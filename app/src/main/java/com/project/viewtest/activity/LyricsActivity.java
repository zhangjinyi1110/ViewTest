package com.project.viewtest.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.project.viewtest.R;
import com.project.viewtest.widget.LyricsView;

public class LyricsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lyrics);
        final LyricsView lyricsView = findViewById(R.id.lyrics_view);
        findViewById(R.id.lyrics_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        findViewById(R.id.lyrics_last).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lyricsView.last();
            }
        });
        findViewById(R.id.lyrics_next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lyricsView.next();
            }
        });
    }
}

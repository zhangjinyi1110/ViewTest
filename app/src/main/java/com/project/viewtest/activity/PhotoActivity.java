package com.project.viewtest.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.project.viewtest.R;
import com.project.viewtest.photo.SelectPhoto;

public class PhotoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        SelectPhoto.get(this);
    }
}

package com.project.viewtest.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.project.viewtest.R;
import com.project.viewtest.photo.MediaHelper;
import com.project.viewtest.photo.Photo;
import com.project.viewtest.photo.SelectPhoto;

import java.util.List;

public class PhotoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        List<Photo> photos = MediaHelper.queryPhoto(this).queryAll();
        if (photos != null) {
            Log.e("aaa", "onCreate: " + photos.size());
        }
    }
}

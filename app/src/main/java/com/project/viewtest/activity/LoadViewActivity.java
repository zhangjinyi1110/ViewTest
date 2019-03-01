package com.project.viewtest.activity;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.project.viewtest.R;
import com.project.viewtest.databinding.ActivityLoadViewBinding;
import com.project.viewtest.widget.LoadDialog;

public class LoadViewActivity extends AppCompatActivity {

    private ActivityLoadViewBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_load_view);
        final LoadDialog dialog = new LoadDialog(this);
        dialog.setCanceledOnTouchOutside(true);
        binding.show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });
        binding.dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
}

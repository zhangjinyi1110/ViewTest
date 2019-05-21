package com.project.viewtest.photo;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.project.viewtest.R;

public class PhotoDialogFragment extends DialogFragment implements View.OnClickListener {

    private ImageView imageView;
    private String url;

    public static PhotoDialogFragment newInstance(String url) {
        Bundle args = new Bundle();
        args.putString("url", url);
        PhotoDialogFragment fragment = new PhotoDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            url = getArguments().getString("url");
        }
    }

    @Override
    public void onResume() {
        WindowManager.LayoutParams layoutParams = getDialog().getWindow().getAttributes();
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
        getDialog().getWindow().setAttributes(layoutParams);
        super.onResume();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getDialog().getWindow() != null)
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        View view = inflater.inflate(R.layout.item_image, container);
//        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//        view.setLayoutParams(layoutParams);
        imageView = view.findViewById(R.id.image);
        imageView.setOnClickListener(this);
        Glide.with(getContext()).load(url).into(imageView);
        return view;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//        View view = LayoutInflater.from(getActivity()).inflate(R.layout.item_image, null);
//        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//        view.setLayoutParams(layoutParams);
//        imageView = view.findViewById(R.id.image);
//        imageView.setOnClickListener(this);
//        Glide.with(getActivity()).load(url).into(imageView);
//        builder.setView(view);
//        return builder.create();
        return super.onCreateDialog(savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        dismiss();
    }
}

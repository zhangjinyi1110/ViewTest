package com.project.viewtest.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.project.viewtest.R;

public class LoadDialog {

    private Context context;
    private View view;
    private Dialog dialog;
    private String tips;
    private TextView tipText;

    public LoadDialog(Context context) {
        this(context, "玩命加载中...");
    }

    public LoadDialog(Context context, View view) {
        this.context = context;
        this.view = view;
        init();
    }

    public LoadDialog(Context context, String tips) {
        this.context = context;
        this.tips = tips;
        init();
    }

    private void init() {
        dialog = new Dialog(context, R.style.dialog);
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.load_view, null);
        }
        if (!TextUtils.isEmpty(tips)) {
            tipText = view.findViewById(R.id.load_tips);
            tipText.setText(tips);
            tipText.setVisibility(View.VISIBLE);
        }
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(false);
    }

    public void setCanceledOnTouchOutside(boolean cancel) {
        dialog.setCanceledOnTouchOutside(cancel);
    }

    public void show() {
        if (!dialog.isShowing())
            dialog.show();
    }

    public void dismiss() {
        if (dialog.isShowing())
            dialog.dismiss();
    }

    public void setOnCancelListener(DialogInterface.OnCancelListener cancelListener) {
        dialog.setOnCancelListener(cancelListener);
    }

    public void setOnDismissListener(DialogInterface.OnDismissListener dismissListener) {
        dialog.setOnDismissListener(dismissListener);
    }

    public void setOnShowListener(DialogInterface.OnShowListener showListener) {
        dialog.setOnShowListener(showListener);
    }

    public void setOnKeyListener(DialogInterface.OnKeyListener keyListener) {
        dialog.setOnKeyListener(keyListener);
    }

}

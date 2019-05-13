package com.project.viewtest.statusbar;

import android.app.Activity;
import android.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

public class BarHelper {

    public static StatusBar with(Activity activity) {
        Window window = activity.getWindow();
        View d = window.getDecorView();
        ViewGroup contentView = d.findViewById(Window.ID_ANDROID_CONTENT);
        if (contentView.getChildCount() > 0) {
            View content = contentView.getChildAt(0);
            if (content instanceof StatusBar) {
                return (StatusBar) content;
            }
        }
        return new ContentView(activity);
    }

    public static StatusBar with(Fragment fragment) {
        Activity activity = fragment.getActivity();
        if (activity == null) {
            throw new NullPointerException("The fragment parent activity is null");
        }
        return with(activity);
    }

}

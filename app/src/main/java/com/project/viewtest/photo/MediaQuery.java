package com.project.viewtest.photo;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import java.util.List;

public abstract class MediaQuery<T extends Media> {

    private Context context;

    MediaQuery(Context context) {
        this.context = context;
    }

    abstract Uri getUri();

    abstract List<T> query(int start, int count, boolean all);

    public List<T> query(int page, int count) {
        return query(page * count - count, count, false);
    }

    public List<T> queryAll() {
        return query(0, 0, true);
    }

    Cursor getCursor() {
        return context.getContentResolver().query(getUri(), null, null, null, null);
    }

}

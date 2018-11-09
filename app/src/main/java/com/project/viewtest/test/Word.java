package com.project.viewtest.test;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by Administrator on 2018/11/8.
 * 单词表
 */
@Entity(tableName = "word_table")
public class Word {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "word")
    private String word;

    @Ignore
    public Word() {
    }

    public Word(@NonNull String word) {
        this.word = word;
    }

    @NonNull
    public String getWord() {
        return word;
    }

    public void setWord(@NonNull String word) {
        this.word = word;
    }
}

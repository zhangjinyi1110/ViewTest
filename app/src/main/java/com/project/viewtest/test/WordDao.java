package com.project.viewtest.test;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by Administrator on 2018/11/8.
 * 单词数据操作接口
 */
@Dao
public interface WordDao {

    @Insert
    void insert(Word word);

    @Query("SELECT * from word_table ORDER BY word ASC")
    LiveData<List<Word>> getAllWords();

    @Delete
    void delete(Word word);

    @Query("SELECT * from word_table ORDER BY word ASC")
    Flowable<List<Word>> getWords();

}

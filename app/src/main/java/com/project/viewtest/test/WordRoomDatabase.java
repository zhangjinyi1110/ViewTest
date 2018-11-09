package com.project.viewtest.test;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

/**
 * Created by Administrator on 2018/11/8.
 * 单词库
 */
@Database(entities = {Word.class}, version = 1, exportSchema = false)
public abstract class WordRoomDatabase extends RoomDatabase {

    public abstract WordDao wordDao();

    private static WordRoomDatabase database;

    public static WordRoomDatabase getDatabase(Context context){
        if(database==null){
            synchronized (WordRoomDatabase.class){
                if(database==null){
                    database = Room.databaseBuilder(context.getApplicationContext()
                            , WordRoomDatabase.class, "word_database")
                            .build();
                }
            }
        }
        return database;
    }

}

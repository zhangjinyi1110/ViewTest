package com.project.viewtest.test;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/11/8.
 * 单词仓库
 */

public class WordRepository {

    private WordDao wordDao;
    private LiveData<List<Word>> liveData;

    public WordRepository(Application application) {
        WordRoomDatabase database = WordRoomDatabase.getDatabase(application);
        wordDao = database.wordDao();
        liveData = wordDao.getAllWords();
        wordDao.getWords()
                .subscribeOn(Schedulers.computation())
                .subscribe(new Consumer<List<Word>>() {
                    @Override
                    public void accept(List<Word> words) throws Exception {
                        liveData = new MutableLiveData<>();
                        if (liveData.getValue() != null) {
                            liveData.getValue().addAll(words);
                        }
                    }
                });
    }

    public LiveData<List<Word>> getLiveData() {
        return liveData;
    }

    public void insert(Word word) {
        Observable.just(word)
                .subscribeOn(Schedulers.computation())
                .subscribe(new Consumer<Word>() {
                    @Override
                    public void accept(Word word) throws Exception {
                        wordDao.insert(word);
                    }
                });
    }

    public void delete(Word word) {
        Observable.just(word)
                .subscribeOn(Schedulers.computation())
                .subscribe(new Consumer<Word>() {
                    @Override
                    public void accept(Word word) throws Exception {
                        wordDao.delete(word);
                    }
                });
    }

}

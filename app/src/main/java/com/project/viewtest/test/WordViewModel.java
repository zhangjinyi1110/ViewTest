package com.project.viewtest.test;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

/**
 * Created by Administrator on 2018/11/8.
 * 单词ViewModel
 */

public class WordViewModel extends AndroidViewModel {

    private WordRepository repository;
    private LiveData<List<Word>> liveData;

    public WordViewModel(@NonNull Application application) {
        super(application);
        repository = new WordRepository(application);
        liveData = repository.getLiveData();
    }

    public LiveData<List<Word>> getLiveData() {
        return liveData;
    }

    public void insert(Word word){
        repository.insert(word);
    }

    public void delete(Word word){
        repository.delete(word);
    }
}

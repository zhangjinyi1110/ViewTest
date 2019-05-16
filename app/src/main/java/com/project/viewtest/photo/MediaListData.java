package com.project.viewtest.photo;

import java.util.List;

public class MediaListData<T> {

    private int count;
    private List <T> datas;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<T> getDatas() {
        return datas;
    }

    public void setDatas(List<T> datas) {
        this.datas = datas;
    }
}

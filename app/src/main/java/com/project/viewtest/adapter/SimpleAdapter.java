package com.project.viewtest.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Administrator on 2018/10/31.
 * SimpleAdapter
 */

public abstract class SimpleAdapter<T, B extends ViewDataBinding> extends RecyclerView.Adapter<SimpleAdapter.ViewHolder> {

    private List<T> list;
    private Context context;

    public SimpleAdapter(Context context) {
        this.context = context;
    }

    public SimpleAdapter(List<T> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        B binding = DataBindingUtil.inflate(LayoutInflater.from(context), getLayoutId(), parent, false);
        return new ViewHolder(binding);
    }

    protected abstract int getLayoutId();

    @SuppressWarnings("unchecked")
    @Override
    public void onBindViewHolder(@NonNull SimpleAdapter.ViewHolder holder, int position, @NonNull List<Object> payloads) {
        if (payloads.isEmpty())
            super.onBindViewHolder(holder, position, payloads);
        else
            convert((B) holder.binding, list.get(position), position, payloads);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onBindViewHolder(@NonNull SimpleAdapter.ViewHolder holder, int position) {
        convert((B) holder.binding, list.get(position), position);
    }

    protected void convert(B binding, T t, int position, List<Object> payloads) {

    }

    protected abstract void convert(B binding, T t, int position);

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public void setList(List<T> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @SuppressWarnings("unchecked")
    class ViewHolder extends RecyclerView.ViewHolder {
        B binding;

        ViewHolder(ViewDataBinding itemView) {
            super(itemView.getRoot());
            binding = (B) itemView;
        }
    }
}

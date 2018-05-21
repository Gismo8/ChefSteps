package com.example.gismo.chefsteps.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public abstract class RecyclerViewAdapterBase<T, V extends View> extends RecyclerView.Adapter<ViewWrapper<V>> {

    protected List<T> items = new ArrayList<>();

    private AdapterChanged callback;

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public final ViewWrapper<V> onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewWrapper<V>(onCreateItemView(parent, viewType));
    }

    protected abstract V onCreateItemView(ViewGroup parent, int viewType);

    public void add(T item) {
        items.add(item);
        onChange();
    }

    public void set(int position, T item) {
        items.set(position, item);
        onChange();
    }

    public void add(int position, T item) {
        items.add(position, item);
        onChange();
    }

    public void setWithoutNotifying(int position, T item) {
        items.set(position, item);
    }

    public void addAll(List<? extends T> item) {
        items.addAll(item);
        onChange();
    }

    public void clear() {
        items.clear();
        onChange();
    }

    public T getItem(int index) {
        return items.get(index);
    }

    public int size() {
        return items.size();
    }

    public void setOnChangeListener(AdapterChanged callback) {
        this.callback = callback;
    }

    protected void onChange() {
        if (callback != null) {
            callback.onChange();
        }
        //notifyDataSetChanged();
    }

    public interface AdapterChanged {
        public void onChange();
    }
}
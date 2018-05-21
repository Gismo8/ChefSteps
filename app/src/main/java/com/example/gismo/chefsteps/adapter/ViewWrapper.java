package com.example.gismo.chefsteps.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by test on 2017. 12. 10..
 */

public class ViewWrapper<V extends View> extends RecyclerView.ViewHolder {

    private V view;

    public ViewWrapper(V itemView) {
        super(itemView);
        view = itemView;
    }

    public V getView() {
        return view;
    }
}

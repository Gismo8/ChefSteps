package com.example.gismo.chefsteps.adapter;

import android.view.ViewGroup;

import com.example.gismo.chefsteps.network.model.RecipeDetail;
import com.example.gismo.chefsteps.ui.view.DetailView;

/**
 * Created by gismo on 2018. 05. 22..
 */

public class DetailAdapter extends RecyclerViewAdapterBase<RecipeDetail, DetailView> {

    @Override
    protected DetailView onCreateItemView(ViewGroup parent, int viewType) {
        return  new DetailView(parent.getContext());
    }

    @Override
    public void onBindViewHolder(ViewWrapper<DetailView> holder, int position) {
        holder.getView().bind(getItem(position), position, position == getItemCount() - 1 ? true : false);
    }
}

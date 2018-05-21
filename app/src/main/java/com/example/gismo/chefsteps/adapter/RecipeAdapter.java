package com.example.gismo.chefsteps.adapter;

import android.view.ViewGroup;

import com.example.gismo.chefsteps.network.model.Recipe;
import com.example.gismo.chefsteps.ui.view.RecipeView;

/**
 * Created by gismo on 2018. 05. 21..
 */

public class RecipeAdapter extends RecyclerViewAdapterBase<Recipe, RecipeView> {

    @Override
    protected RecipeView onCreateItemView(ViewGroup parent, int viewType) {
        return new RecipeView(parent.getContext());
    }

    @Override
    public void onBindViewHolder(ViewWrapper<RecipeView> holder, int position) {
        holder.getView().bind(getItem(position));
    }
}

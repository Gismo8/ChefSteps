package com.example.gismo.chefsteps.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;

import com.example.gismo.chefsteps.RecipeDetailsActivity;
import com.example.gismo.chefsteps.RecipesListActivity;
import com.example.gismo.chefsteps.network.model.Recipe;
import com.example.gismo.chefsteps.ui.view.RecipeView;

import java.io.Serializable;

/**
 * Created by gismo on 2018. 05. 21..
 */

public class RecipeAdapter extends RecyclerViewAdapterBase<Recipe, RecipeView> {

    public static final String RECIPE = "recipe";
    Context context;

    public RecipeAdapter(Context context) {
        this.context = context;
    }

    @Override
    protected RecipeView onCreateItemView(ViewGroup parent, int viewType) {
        return new RecipeView(parent.getContext());
    }

    @Override
    public void onBindViewHolder(ViewWrapper<RecipeView> holder, final int position) {
        holder.getView().bind(getItem(position));
    }
}

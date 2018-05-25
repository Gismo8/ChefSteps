package com.example.gismo.chefsteps;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gismo.chefsteps.adapter.DetailAdapter;
import com.example.gismo.chefsteps.core.ChefStepFragment;
import com.example.gismo.chefsteps.network.model.IngredientWrapper;
import com.example.gismo.chefsteps.network.model.Recipe;
import com.example.gismo.chefsteps.network.model.RecipeDetail;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by gismo on 2018. 05. 22..
 */

public class RecipeDetailsListFragment extends ChefStepFragment {

    @BindView(R.id.mRecyclerView)
    RecyclerView recyclerView;

    protected Recipe recipe;

    protected DetailAdapter detailAdapter;

    public RecipeDetailsListFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_details_list, container, false);
        ButterKnife.bind(this, view);
        detailAdapter = new DetailAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setAdapter(detailAdapter);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager.setAutoMeasureEnabled(false);
        recyclerView.setLayoutManager(linearLayoutManager);
        if (recipe != null) {
            bind(recipe);
        }
        return view;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public void bind(Recipe recipe) {
        this.recipe = recipe;
        if(detailAdapter != null) {
            detailAdapter.add(new IngredientWrapper(String.valueOf(recipe.getIngredients().size()), recipe.getIngredients()));
            detailAdapter.addAll(recipe.getSteps());
            detailAdapter.notifyDataSetChanged();
        }
    }
}

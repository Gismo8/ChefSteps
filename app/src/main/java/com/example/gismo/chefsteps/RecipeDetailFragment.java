package com.example.gismo.chefsteps;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.gismo.chefsteps.adapter.DetailAdapter;
import com.example.gismo.chefsteps.core.ChefStepFragment;
import com.example.gismo.chefsteps.network.model.Ingredient;
import com.example.gismo.chefsteps.network.model.IngredientWrapper;
import com.example.gismo.chefsteps.network.model.Recipe;
import com.example.gismo.chefsteps.network.model.RecipeDetail;
import com.example.gismo.chefsteps.network.model.Step;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by gismo on 2018. 05. 22..
 */

public class RecipeDetailFragment extends ChefStepFragment {

    @BindView(R.id.title)
    TextView title;

    protected RecipeDetail recipeDetail;

    public RecipeDetailFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_details, container, false);
        ButterKnife.bind(this, view);
        bind(recipeDetail);
        return view;
    }

    public void bind(RecipeDetail recipeDetail) {
        this.recipeDetail = recipeDetail;
        if (title == null) {
            return;
        }
        if (recipeDetail instanceof Step) {
            Step step = (Step) recipeDetail;
            title.setText(step.getShortDescription());
        } else {
            IngredientWrapper ingredientWrapper = (IngredientWrapper) recipeDetail;
            title.setText(ingredientWrapper.getIngredients().get(0).getIngredient());
        }
    }
}

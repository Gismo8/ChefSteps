package com.example.gismo.chefsteps;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.example.gismo.chefsteps.adapter.RecipeAdapter;
import com.example.gismo.chefsteps.core.ChefStepActivity;
import com.example.gismo.chefsteps.network.model.Recipe;
import com.example.gismo.chefsteps.network.model.RecipeDetail;
import com.example.gismo.chefsteps.ui.view.DetailView;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * Created by gismo on 2018. 05. 21..
 */

public class RecipeDetailsActivity extends ChefStepActivity implements DetailView.OnDetailItemClickListener {

    @BindView(R.id.fragmentContainer)
    FrameLayout fragmentContainer;

    protected Recipe recipe;
    protected RecipeDetailsListFragment detailListFragment;
    protected RecipeDetailFragment detailFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_recipe_details);
        ButterKnife.bind(this);


        detailListFragment = new RecipeDetailsListFragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, detailListFragment).commit();

        recipe = (Recipe) getIntent().getSerializableExtra(RecipeAdapter.RECIPE);
        detailListFragment.bind(recipe);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(recipe.getName());

        Timber.log(1, recipe.toString());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
               onBackPressed();
               return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(RecipeDetail recipeDetail) {
        detailFragment = new RecipeDetailFragment();
        detailFragment.bind(recipeDetail);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, detailFragment).commit();
    }

    @Override
    public void onBackPressed() {
        if (detailFragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, detailListFragment).commit();
            detailFragment = null;
        } else {
            super.onBackPressed();
        }
    }
}

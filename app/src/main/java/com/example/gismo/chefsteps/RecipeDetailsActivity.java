package com.example.gismo.chefsteps;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.gismo.chefsteps.adapter.RecipeAdapter;
import com.example.gismo.chefsteps.core.ChefStepActivity;
import com.example.gismo.chefsteps.network.model.Recipe;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * Created by gismo on 2018. 05. 21..
 */

public class RecipeDetailsActivity extends ChefStepActivity {



    @BindView(R.id.fragmentContainer)
    FrameLayout fragmentContainer;

    protected Recipe recipe;
    protected RecipeDetailsFragment detailsFragment;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_recipe_detail);
        ButterKnife.bind(this);

        detailsFragment = new RecipeDetailsFragment();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragmentContainer, detailsFragment).commit();

        recipe = (Recipe) getIntent().getSerializableExtra(RecipeAdapter.RECIPE);
        detailsFragment.bind(recipe);

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


}

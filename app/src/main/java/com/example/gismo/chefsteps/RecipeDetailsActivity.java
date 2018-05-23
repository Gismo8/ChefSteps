package com.example.gismo.chefsteps;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.gismo.chefsteps.adapter.RecipeAdapter;
import com.example.gismo.chefsteps.core.ChefStepActivity;
import com.example.gismo.chefsteps.network.model.Recipe;
import com.example.gismo.chefsteps.network.model.RecipeDetail;
import com.example.gismo.chefsteps.ui.view.DetailView;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * Created by gismo on 2018. 05. 21..
 */

public class RecipeDetailsActivity extends ChefStepActivity implements DetailView.OnDetailItemClickListener {

    @BindView(R.id.fragmentContainer)
    FrameLayout fragmentContainer;

    @BindView(R.id.navigationContainer)
    LinearLayout navigationContainer;

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
        Animation slideInAnimation = AnimationUtils.loadAnimation(getApplication(), R.anim.slide_in_from_bottom);
        navigationContainer.setAnimation(slideInAnimation);
        slideInAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                navigationContainer.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        slideInAnimation.start();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, detailFragment).commit();
    }

    @Override
    public void onBackPressed() {
        if (detailFragment != null) {
            navigationContainer.setVisibility(View.GONE);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, detailListFragment).commit();
            detailFragment = null;

        } else {
            super.onBackPressed();
        }
    }
}

package com.example.gismo.chefsteps;

import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
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
import com.example.gismo.chefsteps.network.model.Step;
import com.example.gismo.chefsteps.ui.view.DetailView;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

/**
 * Created by gismo on 2018. 05. 21..
 */

public class RecipeDetailsActivity extends ChefStepActivity implements DetailView.OnDetailItemClickListener {

    @BindView(R.id.fragmentContainer)
    FrameLayout fragmentContainer;

    @BindView(R.id.previousNavigationContainer)
    LinearLayout previousNavigationContainer;

    @BindView(R.id.nextNavigationContainer)
    LinearLayout nextNavigationContainer;

    @BindView(R.id.navigationContainer)
    LinearLayout navigationContainer;

    private static final String RECIPE_DETAIL = "recipe_detail";

    protected Recipe recipe;
    protected RecipeDetailsListFragment detailListFragment;
    protected RecipeDetailFragment detailFragment;
    protected Step currentStep;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recipe = (Recipe) getIntent().getSerializableExtra(RecipeAdapter.RECIPE);
        if (savedInstanceState != null) {
            currentStep = (Step) savedInstanceState.getSerializable(RECIPE_DETAIL);
        }

        setContentView(R.layout.activity_recipe_details);
        ButterKnife.bind(this);

        detailListFragment = new RecipeDetailsListFragment();

        if (currentStep != null) {
            detailFragment = new RecipeDetailFragment();
            detailFragment.bind(((RecipeDetail) currentStep));
            if (getResources().getConfiguration().orientation == 2) {
                getSupportActionBar().hide();
            } else {
                getSupportActionBar().show();
                animateInNavigationBar();
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, detailFragment).commit();
        } else {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, detailListFragment).commit();
        }

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
    public void onDetailItemClick(RecipeDetail recipeDetail) {
        detailFragment = new RecipeDetailFragment();
        if (recipeDetail instanceof Step) {
            currentStep = (Step) recipeDetail;
        }
        detailFragment.bind(recipeDetail);
        animateInNavigationBar();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, detailFragment).commit();
    }

    private void animateInNavigationBar() {
        bindButtonState();
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
    }

    @OnClick(R.id.nextNavigationContainer)
    public void showNextStepFragment() {
        if (!isCurrentStepLast()) {
            currentStep = recipe.getSteps().get(getCurrentStepIndex() + 1);
            detailFragment.bind(currentStep);
            previousNavigationContainer.setEnabled(true);
            bindButtonState();
        } else {
            nextNavigationContainer.setEnabled(false);
        }
    }

    @OnClick(R.id.previousNavigationContainer)
    public void showPreviousStepFragment() {
        if (!isCurrentStepFirst()) {
            currentStep = recipe.getSteps().get(getCurrentStepIndex() - 1);
            detailFragment.bind(currentStep);
            nextNavigationContainer.setEnabled(true);
            bindButtonState();
        } else {
            previousNavigationContainer.setEnabled(false);
        }
    }

    public void bindButtonState() {
        previousNavigationContainer.setBackgroundColor(getResources().getColor(isCurrentStepFirst() ? R.color.inactive : R.color.colorPrimaryDark));
        nextNavigationContainer.setBackgroundColor(getResources().getColor(isCurrentStepLast() ? R.color.inactive : R.color.colorPrimary));
    }

    private boolean isCurrentStepLast() {
        return currentStep.equals(recipe.getSteps().get(recipe.getSteps().size() - 1));
    }

    private boolean isCurrentStepFirst() {
        return currentStep.equals(recipe.getSteps().get(0));
    }

    private int getCurrentStepIndex() {
        int currentStepIndex = - 1;
        for (int i = 0; i  < recipe.getSteps().size(); i++) {
            if (recipe.getSteps().get(i).equals(currentStep)) {
                currentStepIndex = i;
            }
        }
        return currentStepIndex;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putSerializable(RECIPE_DETAIL, currentStep);
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

package com.example.gismo.chefsteps;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
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
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by gismo on 2018. 05. 22..
 */

public class RecipeDetailFragment extends ChefStepFragment {

    @BindView(R.id.title)
    TextView title;

    @BindView(R.id.playerView)
    SimpleExoPlayerView playerView;

    protected RecipeDetail recipeDetail;
    protected SimpleExoPlayer player;

    public RecipeDetailFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_details, container, false);
        ButterKnife.bind(this, view);
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory =
                new AdaptiveTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector =
                new DefaultTrackSelector(videoTrackSelectionFactory);

        player = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector);
        playerView.setPlayer(player);
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
            title.setText(step.getDescription());
            if (player != null) {
                preparePlayer(step.getVideoURL());
                player.setPlayWhenReady(true);
            }
        } else {
            IngredientWrapper ingredientWrapper = (IngredientWrapper) recipeDetail;
            title.setText(ingredientWrapper.getIngredients().get(0).getIngredient());
        }
    }

    public void preparePlayer(String url) {
        DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(getContext(),
                Util.getUserAgent(getContext(), "ChefSteps"), bandwidthMeter);
        MediaSource videoSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                .createMediaSource(Uri.parse(url));
        player.prepare(videoSource);
    }
}

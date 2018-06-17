package com.example.gismo.chefsteps;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.gismo.chefsteps.adapter.DetailAdapter;
import com.example.gismo.chefsteps.core.ChefStepFragment;
import com.example.gismo.chefsteps.network.model.Ingredient;
import com.example.gismo.chefsteps.network.model.IngredientWrapper;
import com.example.gismo.chefsteps.network.model.Recipe;
import com.example.gismo.chefsteps.network.model.RecipeDetail;
import com.example.gismo.chefsteps.network.model.Step;
import com.example.gismo.chefsteps.utils.UiUtils;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
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
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * Created by gismo on 2018. 05. 22..
 */

public class RecipeDetailFragment extends ChefStepFragment {

    @BindView(R.id.title)
    TextView title;

    @BindView(R.id.description)
    TextView description;

    @BindView(R.id.playerView)
    SimpleExoPlayerView playerView;

    @BindView(R.id.thumbNail)
    ImageView thumbNail;

    @BindView(R.id.thumbNailLayer)
    FrameLayout thumbNailLayer;

    protected RecipeDetail recipeDetail;
    protected SimpleExoPlayer player;
    protected boolean twoPane;

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

        if (getResources().getConfiguration().orientation == 2 && !twoPane) {
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)
                    playerView.getLayoutParams();
            params.width = params.MATCH_PARENT;
            params.height = params.MATCH_PARENT;
            playerView.setLayoutParams(params);
        } else if (twoPane) {
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)
                    playerView.getLayoutParams();
            params.width = params.MATCH_PARENT;
            params.height = getResources().getDisplayMetrics().heightPixels / 2;
            playerView.setLayoutParams(params);
            thumbNailLayer.setVisibility(View.GONE);
        }
        return view;
    }

    public void bind(RecipeDetail recipeDetail) {

        this.recipeDetail = recipeDetail;
        if (title == null || recipeDetail == null) {
            return;
        }

        if (recipeDetail instanceof Step) {
            Step step = (Step) recipeDetail;
            title.setText(step.getShortDescription());
            description.setText(step.getDescription());

            if (step.getThumbnailURL() != null && !step.getThumbnailURL().equals("")) {
                Picasso.get().load(step.getThumbnailURL()).centerCrop().resize(((int) (UiUtils.getScreenWidth())), thumbNail.getHeight()).into(thumbNail);
            } else {
                thumbNailLayer.setVisibility(View.GONE);
            }

            if (player != null) {
                player.setPlayWhenReady(true);
                preparePlayer(step.getVideoURL());
                player.addListener(new Player.DefaultEventListener() {
                    @Override
                    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                        if (playbackState == Player.STATE_READY) {
                            thumbNailLayer.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onPositionDiscontinuity(int reason) {
                        if (reason == Player.DISCONTINUITY_REASON_PERIOD_TRANSITION) {
                        }
                    }
                });
            }
        }
    }

    public void preparePlayer(String url) {
        player.seekTo(((RecipeDetailsActivity) getActivity()).getPlayerPosition());
        DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(getContext(),
                Util.getUserAgent(getContext(), "ChefSteps"), bandwidthMeter);
        MediaSource videoSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                .createMediaSource(Uri.parse(url));
        player.prepare(videoSource);
    }

    @OnClick(R.id.thumbNailLayer)
    public void startVideo() {
        if(player != null) {
            thumbNailLayer.setVisibility(View.GONE);
            player.setPlayWhenReady(true);
        }
    }


    @Override
    public void onStop() {
        player.release();
        super.onStop();
    }

    public void setTwoPane() {
        twoPane = true;
    }

}

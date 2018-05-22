package com.example.gismo.chefsteps;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gismo.chefsteps.adapter.DetailAdapter;
import com.example.gismo.chefsteps.core.ChefStepFragment;

import butterknife.BindView;

/**
 * Created by gismo on 2018. 05. 22..
 */

public class RecipeDetailsFragment extends ChefStepFragment {


    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    protected DetailAdapter detailAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        inflater.inflate(R.layout.fragment_recipe_details, container, false);
        return super.onCreateView(inflater, container, savedInstanceState);
    }


}

package com.example.gismo.chefsteps.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import com.example.gismo.chefsteps.R;
import com.example.gismo.chefsteps.network.model.RecipeDetail;

/**
 * Created by gismo on 2018. 05. 22..
 */

public class DetailView extends RelativeLayout {

    public DetailView(Context context) {
        super(context);
        init();
    }

    public void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_detail, this, true);
    }

    public void bind(RecipeDetail recipeDetail) {

    }

}

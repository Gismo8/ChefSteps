package com.example.gismo.chefsteps.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.gismo.chefsteps.R;
import com.example.gismo.chefsteps.network.model.Recipe;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * Created by gismo on 2018. 05. 21..
 */

public class RecipeView extends RelativeLayout {

    @BindView(R.id.imageView)
    ImageView imageView;

    @BindView(R.id.recipeName)
    TextView recipeName;

    public RecipeView(Context context) {
        super(context);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_recipe, this, true);
        ButterKnife.bind(this);
    }

    public void bind(Recipe recipe) {
        Timber.log(1, recipe.getName() + " binded ");
        recipeName.setText(recipe.getName());
    }

}

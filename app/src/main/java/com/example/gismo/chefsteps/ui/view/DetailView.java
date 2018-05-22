package com.example.gismo.chefsteps.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.gismo.chefsteps.R;
import com.example.gismo.chefsteps.network.model.Ingredient;
import com.example.gismo.chefsteps.network.model.IngredientWrapper;
import com.example.gismo.chefsteps.network.model.RecipeDetail;
import com.example.gismo.chefsteps.network.model.Step;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by gismo on 2018. 05. 22..
 */

public class DetailView extends RelativeLayout {

    @BindView(R.id.root)
    RelativeLayout root;

    @BindView(R.id.stepIndex)
    TextView stepIndex;

    @BindView(R.id.icon)
    ImageView icon;

    @BindView(R.id.shortDescription)
    TextView shortDescription;

    Step step;
    IngredientWrapper ingredientWrapper;

    public DetailView(Context context) {
        super(context);
        init();
    }

    public void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_detail, this, true);
        ButterKnife.bind(this);
    }

    public void bind(RecipeDetail recipeDetail, int position, boolean isLastItem) {
        if (recipeDetail instanceof Step) {
            step = (Step) recipeDetail;
            shortDescription.setText(step.getShortDescription());
            stepIndex.setText(getResources().getString(R.string.steps, String.valueOf(position)));
            icon.setImageResource(isLastItem ? R.drawable.ready : R.drawable.play);
        } else {
            ingredientWrapper = (IngredientWrapper) recipeDetail;
            shortDescription.setVisibility(GONE);
            stepIndex.setText(getResources().getString(R.string.details_ingredients, ingredientWrapper.getIngredientCount()));
            stepIndex.setGravity(CENTER_VERTICAL);
            root.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
            icon.setImageResource(R.drawable.ingredients);
        }
    }

}

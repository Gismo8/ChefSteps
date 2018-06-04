package com.example.gismo.chefsteps.ui.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.gismo.chefsteps.R;
import com.example.gismo.chefsteps.RecipeDetailsActivity;
import com.example.gismo.chefsteps.network.model.IngredientWrapper;
import com.example.gismo.chefsteps.network.model.RecipeDetail;
import com.example.gismo.chefsteps.network.model.Step;
import com.example.gismo.chefsteps.utils.RecipeUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by gismo on 2018. 05. 22..
 */

public class DetailView extends RelativeLayout {

    @BindView(R.id.root)
    RelativeLayout root;

    @BindView(R.id.detailTitle)
    TextView detailTitle;

    @BindView(R.id.icon)
    ImageView icon;

    @BindView(R.id.shortDescription)
    TextView shortDescription;

    RecipeDetail recipeDetail;
    IngredientWrapper ingredientWrapper;

    OnDetailItemClickListener onDetailItemClickListener;

    public DetailView(Context context) {
        super(context);
        init();
    }

    public void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_detail, this, true);
        onDetailItemClickListener = (RecipeDetailsActivity) getContext();
        ButterKnife.bind(this);
    }

    public void bind(final RecipeDetail recipeDetail, int position, boolean isLastItem) {
        if (recipeDetail instanceof Step) {
            shortDescription.setText(((Step) recipeDetail).getShortDescription());
            shortDescription.setMaxLines(1);
            detailTitle.setText(getResources().getString(R.string.steps, String.valueOf(position)));
            icon.setImageResource(isLastItem ? R.drawable.ready : R.drawable.play);
            icon.setVisibility(VISIBLE);
            root.setBackgroundColor(getResources().getColor(R.color.stepsBackGround));
            root.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    onDetailItemClickListener.onDetailItemClick(recipeDetail);
                }
            });
        } else {
            ingredientWrapper = (IngredientWrapper) recipeDetail;
            shortDescription.setMaxLines(Integer.MAX_VALUE);
            detailTitle.setText(getResources().getString(R.string.details_ingredients, ingredientWrapper.getIngredientCount()));
            shortDescription.setText(RecipeUtils.getIngredients(ingredientWrapper));
            detailTitle.setGravity(CENTER_VERTICAL);
            root.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
            icon.setImageResource(R.drawable.ingredients);
            icon.setVisibility(GONE);
            root.setOnClickListener(null);
        }
    }


    public interface OnDetailItemClickListener {
        void onDetailItemClick(RecipeDetail recipeDetail);
    }


}

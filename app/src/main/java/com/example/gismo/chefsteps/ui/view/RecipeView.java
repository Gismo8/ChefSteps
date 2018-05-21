package com.example.gismo.chefsteps.ui.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.gismo.chefsteps.R;
import com.example.gismo.chefsteps.network.model.Recipe;
import com.example.gismo.chefsteps.utils.UiUtils;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * Created by gismo on 2018. 05. 21..
 */

public class RecipeView extends RelativeLayout {

    private static final String BROWNIES_URL = "https://source.unsplash.com/fDW5C0QlPGI/1600x900";
    private static final String NUTELLA_PIE_URL = "https://source.unsplash.com/7JYVKRo7i5Q/1600x900";
    private static final String YELLOW_CAKE_URL = "https://source.unsplash.com/k-q63hBFSB8/1600x900";
    private static final String CHEESE_CAKE_URL = "https://source.unsplash.com/xVLJ-JFTcQE/1600x900";
    private static final String RANDOM_DESSERT_URL = "https://source.unsplash.com/collection/941995/1600x900";

    @BindView(R.id.imageView)
    ImageView imageView;

    @BindView(R.id.recipeName)
    TextView recipeName;

    @BindView(R.id.servings)
    TextView servings;

    @BindView(R.id.ingredients)
    TextView ingredients;


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
        servings.setText(getResources().getString(R.string.servings, String.valueOf(recipe.getServings())));
        ingredients.setText(getResources().getString(R.string.ingredients, recipe.getIngredientsString()));


        if (recipe.getImage() == null || recipe.getImage().equals("")) {
            switch (recipe.getName()) {
                case "Brownies":
                    loadImage(BROWNIES_URL);
                    break;
                case "Yellow Cake":
                    loadImage(YELLOW_CAKE_URL);
                    break;
                case "Cheesecake":
                    loadImage(CHEESE_CAKE_URL);
                    break;
                case "Nutella Pie":
                    loadImage(NUTELLA_PIE_URL);
                    break;
                default:
                    loadImage(RANDOM_DESSERT_URL);
                    break;
            }
        } else {
            loadImage(recipe.getImage());
        }
    }

    private void loadImage(String url) {
        Picasso.get().load(url).centerCrop().resize(((int) (UiUtils.getScreenWidth() * 0.95)), imageView.getHeight()).into(imageView);
    }

}

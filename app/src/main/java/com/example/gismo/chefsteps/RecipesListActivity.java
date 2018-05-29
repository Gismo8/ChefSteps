package com.example.gismo.chefsteps;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import com.example.gismo.chefsteps.adapter.RecipeAdapter;
import com.example.gismo.chefsteps.core.ChefStepActivity;
import com.example.gismo.chefsteps.network.model.Recipe;
import com.example.gismo.chefsteps.utils.JSONUtils;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.pm.ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
import static com.example.gismo.chefsteps.adapter.RecipeAdapter.RECIPE;
import static com.example.gismo.chefsteps.widget.IngredientsWidget.RECIPE_NAME;

public class RecipesListActivity extends ChefStepActivity {

    protected static final String TAG = RecipesListActivity.class.getSimpleName();
    protected RecipeAdapter adapter;
    protected List<Recipe> recipes;
    protected String remoteRecipeName = "";

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes_list);
        ButterKnife.bind(this);

        remoteRecipeName = getIntent().getStringExtra(RECIPE_NAME);

        adapter = new RecipeAdapter(this);

        try {
            recipes = JSONUtils.getModelFromJson(JSONUtils.getJsonAsset(this));
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (remoteRecipeName != null && !remoteRecipeName.equals("")) {
                Recipe intentRecipe = null;
            for (Recipe recipe : recipes)  {
                    if (remoteRecipeName.equals(recipe.getName())) {
                        intentRecipe = recipe;
                    }
                }
                if (intentRecipe != null) {
                Intent intent = new Intent(this, RecipeDetailsActivity.class);
                intent.putExtra(RECIPE, (Serializable) intentRecipe);
                startActivity(intent);
                }
        }

        adapter.addAll(recipes);
        GridLayoutManager layoutManager = new GridLayoutManager(this,
                getResources().getConfiguration().orientation == 2 ? 3 : 1,
                LinearLayoutManager.VERTICAL,
                false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.requestLayout();
    }
}

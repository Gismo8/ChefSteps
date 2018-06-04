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
import com.example.gismo.chefsteps.core.ChefStepApplication;
import com.example.gismo.chefsteps.di.NetComponent;
import com.example.gismo.chefsteps.network.model.Recipe;
import com.example.gismo.chefsteps.network.model.rest.RestApi;
import com.example.gismo.chefsteps.utils.JSONUtils;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.content.pm.ActivityInfo.LAUNCH_MULTIPLE;
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

    @Inject
    Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes_list);
        ButterKnife.bind(this);

        ((ChefStepApplication) getApplication()).getNetComponent().inject(this);

        adapter = new RecipeAdapter(this);
        GridLayoutManager layoutManager = new GridLayoutManager(this,
                getResources().getConfiguration().orientation == 2 ? 3 : 1,
                LinearLayoutManager.VERTICAL,
                false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        //Create a retrofit call object
        Call<List<Recipe>> posts = retrofit.create(RestApi.class).getRecipes();

        //Enque the call
        posts.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                //Set the response to the textview
                recipes = response.body();
                adapter.addAll(recipes);
                adapter.notifyDataSetChanged();
                Log.d(TAG, response.body().get(0).getName());
                Log.d(TAG, "retrofit success");
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                //Set the error to the textview
                Log.d(TAG, t.toString());
                Log.d(TAG, "retrofit failure");
            }
        });

        remoteRecipeName = getIntent().getStringExtra(RECIPE_NAME);



        if (remoteRecipeName != null && !remoteRecipeName.equals("")) {
                Recipe intentRecipe = null;
            for (Recipe recipe : recipes)  {
                    if (remoteRecipeName.equals(recipe.getName())) {
                        intentRecipe = recipe;
                    }
                }
                if (intentRecipe != null) {
                Intent intent = new Intent(this, RecipeDetailsActivity.class);
                intent.setAction(Intent.ACTION_VIEW);
                intent.putExtra(RECIPE, (Serializable) intentRecipe);
                startActivity(intent);
                }
        }



    }
}

package com.example.gismo.chefsteps;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import com.example.gismo.chefsteps.adapter.RecipeAdapter;
import com.example.gismo.chefsteps.core.ChefStepActivity;
import com.example.gismo.chefsteps.network.model.Recipe;
import com.example.gismo.chefsteps.utils.JSONUtils;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipesListActivity extends ChefStepActivity {

    RecipeAdapter adapter;
    List<Recipe> recipes;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes_list);
        ButterKnife.bind(this);

        adapter = new RecipeAdapter(this);

        try {
            recipes = JSONUtils.getModelFromJson(JSONUtils.getJsonAsset(this));
        } catch (IOException e) {
            e.printStackTrace();
        }

        adapter.addAll(recipes);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayout.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.requestLayout();
    }
}

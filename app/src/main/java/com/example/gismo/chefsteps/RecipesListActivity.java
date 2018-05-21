package com.example.gismo.chefsteps;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.gismo.chefsteps.utils.JSONUtils;

import java.io.IOException;

public class RecipesListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes_list);
        try {
            JSONUtils.getModelFromJson(JSONUtils.getJsonAsset(this));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

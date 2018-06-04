package com.example.gismo.chefsteps.network.model.rest;

import android.graphics.LightingColorFilter;

import com.example.gismo.chefsteps.network.model.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RestApi {

    @GET("/topher/2017/May/59121517_baking/baking.json")
    Call<List<Recipe>> getRecipes();
}

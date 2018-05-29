package com.example.gismo.chefsteps.utils;

import com.example.gismo.chefsteps.network.model.Ingredient;
import com.example.gismo.chefsteps.network.model.Recipe;

import java.util.ArrayList;

/**
 * Created by gismo on 2018. 05. 28..
 */

public class RecipeUtils {

    public static ArrayList<String> getIngredientsList(Recipe recipe) {
        ArrayList<String> list = new ArrayList<>();
        for (Ingredient ingredient : recipe.getIngredients()) {
            list.add(ingredient.getIngredient());
        }
        return list;
    }

}

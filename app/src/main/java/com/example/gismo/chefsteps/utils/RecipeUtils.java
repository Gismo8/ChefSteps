package com.example.gismo.chefsteps.utils;

import com.example.gismo.chefsteps.network.model.Ingredient;
import com.example.gismo.chefsteps.network.model.IngredientWrapper;
import com.example.gismo.chefsteps.network.model.Recipe;

import java.util.ArrayList;
import java.util.List;

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

    public static String getIngredients(IngredientWrapper ingredientWrapper) {
        StringBuilder sb = new StringBuilder();
        List<Ingredient> ingredientList = ingredientWrapper.getIngredients();
        for (int i = 0; i < ingredientList.size(); i++) {
            Ingredient ingredient = ingredientList.get(i);
            sb.append(String.valueOf(i + 1) + ". "
                    + ingredient.getIngredient() + " - "
                    + ingredient.getQuantity() + " "
                    + ingredient.getMeasure());
            if (i != ingredientList.size() - 1) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }

}

package com.example.gismo.chefsteps.network.model;

import java.util.List;

/**
 * Created by gismo on 2018. 05. 22..
 */

public class IngredientWrapper implements RecipeDetail {

    String ingredientCount;

    List<Ingredient> ingredients;

    public IngredientWrapper(String ingredientCount, List<Ingredient> ingredients) {
        this.ingredientCount = ingredientCount;
        this.ingredients = ingredients;
    }

    public String getIngredientCount() {
        return ingredientCount;
    }

    public void setIngredientCount(String ingredientCount) {
        this.ingredientCount = ingredientCount;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }
}

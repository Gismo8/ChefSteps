package com.example.gismo.chefsteps.widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import java.util.ArrayList;

/**
 * Created by gismo on 2018. 05. 27..
 */

public class UpdateIngredientWidgetService extends IntentService {

    public static String FROM_ACTIVITY_INGREDIENTS_LIST = "FROM_ACTIVITY_INGREDIENTS_LIST";
    public static String FROM_ACTIVITY_RECIPE_NAME = "FROM_ACTIVITY_RECIPE_NAME";

    public UpdateIngredientWidgetService() {
        super("UpdateIngredientWidgetService");
    }

    public static void startUpdateIngredientService(Context context, ArrayList<String> fromActivityIngredientsList, String recipeName) {
        Intent intent = new Intent(context, UpdateIngredientWidgetService.class);
        intent.putExtra(FROM_ACTIVITY_INGREDIENTS_LIST, fromActivityIngredientsList);
        intent.putExtra(FROM_ACTIVITY_RECIPE_NAME, recipeName);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            ArrayList<String> fromActivityIngredientsList = intent.getExtras().getStringArrayList(FROM_ACTIVITY_INGREDIENTS_LIST);
            String recipeName = intent.getExtras().getString(FROM_ACTIVITY_RECIPE_NAME);
            handleActionUpdateBakingWidgets(fromActivityIngredientsList, recipeName);
        }
    }

    private void handleActionUpdateBakingWidgets(ArrayList<String> fromActivityIngredientsList, String recipeName) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(getApplication());
        int[] ids = appWidgetManager.getAppWidgetIds(new ComponentName(getApplication(), IngredientsWidget.class));
        IngredientsWidget.updateIngredientWidgets(getApplicationContext(), appWidgetManager, fromActivityIngredientsList, recipeName, ids);
    }

}

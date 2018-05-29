package com.example.gismo.chefsteps.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.example.gismo.chefsteps.R;
import com.example.gismo.chefsteps.RecipesListActivity;
import com.example.gismo.chefsteps.network.model.Recipe;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Implementation of App Widget functionality.
 */
public class IngredientsWidget extends AppWidgetProvider {

    public static ArrayList<String> remoteIngredientList = new ArrayList<>();
    public static String remoteRecipeName = "";
    public static String RECIPE_NAME = "RECIPE_NAME";


    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, ArrayList<String> ingredientsList,
                                String recipeName, int appWidgetId) {

        remoteRecipeName = recipeName;
        remoteIngredientList = ingredientsList;
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.listView);

        Intent remoteService = new Intent(context, IngredientRemoteService.class);
        remoteService.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.ingredients_widget);
        views.setRemoteAdapter(R.id.listView, remoteService);
        views.setEmptyView(R.id.listView, R.id.emptyView);
        views.setTextViewText(R.id.recipeName, context.getResources().getString(R.string.ingredients_widget, String.valueOf(recipeName)));

        Intent intent = new Intent(context, RecipesListActivity.class);
        intent.putExtra(RECIPE_NAME, remoteRecipeName);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        views.setPendingIntentTemplate(R.id.listView, pendingIntent);
        views.setOnClickPendingIntent(R.id.emptyView, pendingIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, remoteIngredientList, remoteRecipeName, appWidgetId);
        }
    }

    public static void updateIngredientWidgets(Context context, AppWidgetManager appWidgetManager, ArrayList<String> ingredientsList, String recipeName,  int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, ingredientsList, recipeName, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, IngredientsWidget.class));
        IngredientsWidget.updateIngredientWidgets(context, appWidgetManager, remoteIngredientList, remoteRecipeName, appWidgetIds);
        super.onReceive(context, intent);
    }
}


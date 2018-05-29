package com.example.gismo.chefsteps.widget;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.gismo.chefsteps.R;

import java.util.ArrayList;
import java.util.List;

import static com.example.gismo.chefsteps.widget.IngredientsWidget.remoteIngredientList;

/**
 * Created by gismo on 2018. 05. 27..
 */

public class IngredientRemoteService extends RemoteViewsService {

    List<String> ingredientList;

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new IngredientRemoteViewsFactory(this.getApplicationContext(), intent);
    }

    public class IngredientRemoteViewsFactory implements RemoteViewsFactory {

        Context context;

        public IngredientRemoteViewsFactory(Context context, Intent intent) {
            this.context = context;
        }

        @Override
        public void onCreate() {
            ingredientList = new ArrayList<>();
        }

        @Override
        public void onDataSetChanged() {
            ingredientList = remoteIngredientList;
        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
            return ingredientList.size();
        }

        @Override
        public RemoteViews getViewAt(int i) {
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.view_widget_item);

            views.setTextViewText(R.id.ingredientName, ingredientList.get(i));

            Intent fillInIntent = new Intent();
            views.setOnClickFillInIntent(R.id.ingredientName, fillInIntent);

            return views;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }

}

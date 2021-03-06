package com.example.gismo.chefsteps.utils;

import android.content.Context;

import com.example.gismo.chefsteps.network.model.Recipe;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.LinkedTreeMap;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by gismo on 2018. 05. 21..
 */

public class JSONUtils {

    public static List <Recipe> getModelFromJson(String json) {
        Gson gson = new GsonBuilder().create();
        Recipe[] recipes = gson.fromJson(json, Recipe[].class);
        return  new ArrayList<>(Arrays.asList(recipes));
    }

    public static String getJsonAsset(Context context) throws IOException {
        String json;
        try {
            InputStream is = context.getAssets().open("baking.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
            return json;
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}

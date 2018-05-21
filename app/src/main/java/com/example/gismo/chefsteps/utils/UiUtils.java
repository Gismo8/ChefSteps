package com.example.gismo.chefsteps.utils;

import android.content.Context;

/**
 * Created by gismo on 2018. 05. 21..
 */

public class UiUtils {

    private static int screenWidth;

    public static void measureScreenWidth(Context context) {
        screenWidth = context.getResources().getDisplayMetrics().widthPixels;
    }

    public static int getScreenWidth() {
        return screenWidth;
    }
}

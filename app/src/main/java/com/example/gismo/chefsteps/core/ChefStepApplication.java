package com.example.gismo.chefsteps.core;

import android.app.Application;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.gismo.chefsteps.BuildConfig;
import com.example.gismo.chefsteps.utils.UiUtils;

import timber.log.Timber;

/**
 * Created by gismo on 2018. 05. 21..
 */

public class ChefStepApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
        UiUtils.measureScreenWidth(this);
    }

}

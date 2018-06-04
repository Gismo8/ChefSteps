package com.example.gismo.chefsteps.core;

import android.app.Application;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.gismo.chefsteps.BuildConfig;
import com.example.gismo.chefsteps.di.AppModule;
import com.example.gismo.chefsteps.di.DaggerNetComponent;
import com.example.gismo.chefsteps.di.NetComponent;
import com.example.gismo.chefsteps.di.NetModule;
import com.example.gismo.chefsteps.utils.UiUtils;

import timber.log.Timber;

/**
 * Created by gismo on 2018. 05. 21..
 */

public class ChefStepApplication extends Application {

    private NetComponent netComponent;
    private String baseURL = "https://d17h27t6h515a5.cloudfront.net";

    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
        netComponent = DaggerNetComponent.builder()
                .appModule(new AppModule(this))
                .netModule(new NetModule(baseURL))
                .build();

        UiUtils.measureScreenWidth(this);
    }

    public NetComponent getNetComponent() {
        return netComponent;
    }
}

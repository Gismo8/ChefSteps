package com.example.gismo.chefsteps.di;

import com.example.gismo.chefsteps.RecipesListActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, NetModule.class})
public interface NetComponent {
    void inject(RecipesListActivity activity);
}

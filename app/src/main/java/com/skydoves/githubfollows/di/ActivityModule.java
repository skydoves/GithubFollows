package com.skydoves.githubfollows.di;

import com.skydoves.githubfollows.view.ui.detail.DetailActivity;
import com.skydoves.githubfollows.view.ui.main.MainActivity;
import com.skydoves.githubfollows.view.ui.search.SearchActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Developed by skydoves on 2018-01-20.
 * Copyright (c) 2018 skydoves rights reserved.
 */

@Module
public abstract class ActivityModule {
    @ContributesAndroidInjector
    abstract MainActivity contributeMainActivity();

    @ContributesAndroidInjector
    abstract SearchActivity contributeSearchActivity();

    @ContributesAndroidInjector
    abstract DetailActivity contributeDetailActivity();
}

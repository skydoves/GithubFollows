package com.skydoves.githubfollows.di

import com.skydoves.githubfollows.view.ui.detail.DetailActivity
import com.skydoves.githubfollows.view.ui.main.MainActivity
import com.skydoves.githubfollows.view.ui.search.SearchActivity

import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Developed by skydoves on 2018-01-20.
 * Copyright (c) 2018 skydoves rights reserved.
 */

@Suppress("unused")
@Module
abstract class ActivityModule {
    @ContributesAndroidInjector
    internal abstract fun contributeMainActivity(): MainActivity

    @ContributesAndroidInjector
    internal abstract fun contributeSearchActivity(): SearchActivity

    @ContributesAndroidInjector
    internal abstract fun contributeDetailActivity(): DetailActivity
}

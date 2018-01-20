package com.skydoves.sharedelementtransition.di

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider

import com.skydoves.sharedelementtransition.ui.main.MainActivityViewModel
import com.skydoves.sharedelementtransition.viewmodel.AppViewModelFactory

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Developed by skydoves on 2018-01-20.
 * Copyright (c) 2018 skydoves rights reserved.
 */


@Module
internal abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(MainActivityViewModel::class)
    internal abstract fun bindMainActivityViewModel(mainActivityViewModel: MainActivityViewModel): ViewModel

    @Binds
    internal abstract fun bindViewModelFactory(appViewModelFactory: AppViewModelFactory): ViewModelProvider.Factory
}

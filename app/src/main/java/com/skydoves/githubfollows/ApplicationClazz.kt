package com.skydoves.githubfollows

import com.skydoves.githubfollows.di.DaggerAppComponent
import com.skydoves.githubfollows.preference.PreferenceComponent_PrefAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import timber.log.Timber

/**
 * Developed by skydoves on 2018-01-19.
 * Copyright (c) 2018 skydoves rights reserved.
 */

class ApplicationClazz : DaggerApplication() {

    override fun onCreate() {
        super.onCreate()

        DaggerAppComponent.builder()
                .application(this)
                .build()
                .inject(this)

        if(BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        PreferenceComponent_PrefAppComponent.init(this)
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().application(this).build()
    }
}

package com.skydoves.githubfollows

import com.facebook.stetho.Stetho
import com.skydoves.githubfollows.di.DaggerAppComponent
import com.skydoves.githubfollows.preference.PreferenceComponent_PrefAppComponent
import com.squareup.leakcanary.LeakCanary
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import timber.log.Timber

/**
 * Developed by skydoves on 2018-01-19.
 * Copyright (c) 2018 skydoves rights reserved.
 */

@Suppress("unused")
class GithubFollowsApplication : DaggerApplication() {

  private val appComponent = DaggerAppComponent.builder()
      .application(this)
      .build()

  override fun onCreate() {
    super.onCreate()
    appComponent.inject(this)

    if (!LeakCanary.isInAnalyzerProcess(this)) {
      LeakCanary.install(this)
    }

    if (BuildConfig.DEBUG) {
      Timber.plant(Timber.DebugTree())
    }

    PreferenceComponent_PrefAppComponent.init(this)
    Stetho.initializeWithDefaults(this)
  }

  override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
    return appComponent
  }
}

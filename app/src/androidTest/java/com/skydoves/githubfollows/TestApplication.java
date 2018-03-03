package com.skydoves.githubfollows;

import android.app.Application;

import com.skydoves.githubfollows.preference.PreferenceComponent_PrefAppComponent;

/**
 * Developed by skydoves on 2018-03-03.
 * Copyright (c) 2018 skydoves rights reserved.
 */

/**
 * We use a separate App for tests to prevent initializing dependency injection.
 *
 * See {@link com.skydoves.githubfollows.util.JunitTestRunner}.
 */
public class TestApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        PreferenceComponent_PrefAppComponent.init(this);
    }
}

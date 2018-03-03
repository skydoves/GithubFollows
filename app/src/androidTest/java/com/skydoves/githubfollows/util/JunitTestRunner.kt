package com.skydoves.githubfollows.util

import android.app.Application
import android.content.Context
import android.support.test.runner.AndroidJUnitRunner

import com.skydoves.githubfollows.TestApplication

/**
 * Developed by skydoves on 2018-03-03.
 * Copyright (c) 2018 skydoves rights reserved.
 */

class JunitTestRunner : AndroidJUnitRunner() {
    @Throws(InstantiationException::class, IllegalAccessException::class, ClassNotFoundException::class)
    override fun newApplication(cl: ClassLoader, className: String, context: Context): Application {
        return super.newApplication(cl, TestApplication::class.java.name, context)
    }
}

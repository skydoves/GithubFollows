package com.skydoves.githubfollows.util

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner

import com.skydoves.githubfollows.TestGithubFollowsApplication

/**
 * Developed by skydoves on 2018-03-03.
 * Copyright (c) 2018 skydoves rights reserved.
 */

class JunitTestRunner : AndroidJUnitRunner() {
    @Throws(InstantiationException::class, IllegalAccessException::class, ClassNotFoundException::class)
    override fun newApplication(cl: ClassLoader, className: String, context: Context): Application {
        return super.newApplication(cl, TestGithubFollowsApplication::class.java.name, context)
    }
}

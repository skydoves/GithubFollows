package com.skydoves.githubfollows.extension

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import kotlin.reflect.KClass

/**
 * Developed by skydoves on 2019-06-07.
 * Copyright (c) 2018 skydoves rights reserved.
 */

fun <T : ViewModel> FragmentActivity.vm(factory: ViewModelProvider.Factory, model: KClass<T>): T {
  return ViewModelProviders.of(this, factory).get(model.java)
}

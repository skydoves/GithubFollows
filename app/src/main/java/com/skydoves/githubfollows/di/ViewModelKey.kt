package com.skydoves.githubfollows.di

import androidx.lifecycle.ViewModel
import dagger.MapKey
import kotlin.reflect.KClass

/**
 * Developed by skydoves on 2018-01-20.
 * Copyright (c) 2018 skydoves rights reserved.
 */

@MustBeDocumented
@kotlin.annotation.Retention
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
@MapKey
internal annotation class ViewModelKey(val value: KClass<out ViewModel>)
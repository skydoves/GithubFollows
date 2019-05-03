package com.skydoves.githubfollows.persistence.preference

import android.content.Context
import androidx.test.InstrumentationRegistry
import org.junit.Before

/**
 * Developed by skydoves on 2018-03-03.
 * Copyright (c) 2018 skydoves rights reserved.
 */

abstract class PreferenceTest {

  lateinit var context: Context

  @Before
  fun initPreference() {
    context = InstrumentationRegistry.getTargetContext()
  }
}

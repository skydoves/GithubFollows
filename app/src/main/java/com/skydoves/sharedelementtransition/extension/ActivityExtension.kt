package com.skydoves.sharedelementtransition.extension

import android.app.Activity
import android.os.Build

/**
 * Developed by skydoves on 2018-01-19.
 * Copyright (c) 2018 skydoves rights reserved.
 */

fun Activity.checkVersion() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
package com.skydoves.githubfollows.utils

import android.view.View
import androidx.databinding.BindingConversion

/**
 * Developed by skydoves on 2018-02-18.
 * Copyright (c) 2018 skydoves rights reserved.
 */

object BindingUtils {
  @BindingConversion
  @JvmStatic
  fun bindingVisibility(visible: Boolean): Int {
    return if (visible) View.VISIBLE else View.GONE
  }
}

package com.skydoves.githubfollows.binding

import android.view.View
import androidx.databinding.BindingConversion

/**
 * Developed by skydoves on 2018-02-18.
 * Copyright (c) 2018 skydoves rights reserved.
 */

@BindingConversion
fun bindingVisibility(visible: Boolean): Int {
  return if (visible) View.VISIBLE else View.GONE
}

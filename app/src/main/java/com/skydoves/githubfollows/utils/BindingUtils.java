package com.skydoves.githubfollows.utils;

import android.view.View;

import androidx.databinding.BindingConversion;

/**
 * Developed by skydoves on 2018-02-18. Copyright (c) 2018 skydoves rights reserved.
 */
public class BindingUtils {
  @BindingConversion
  public static int bindingVisibility(boolean visible) {
    return visible ? View.VISIBLE : View.GONE;
  }
}

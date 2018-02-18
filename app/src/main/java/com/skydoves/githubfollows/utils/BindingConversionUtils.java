package com.skydoves.githubfollows.utils;

import android.databinding.BindingConversion;
import android.view.View;

/**
 * Developed by skydoves on 2018-02-18.
 * Copyright (c) 2018 skydoves rights reserved.
 */

public class BindingConversionUtils {
    @BindingConversion
    public static int bindingVisibility(boolean visible) {
        return visible ? View.VISIBLE : View.GONE;
    }
}

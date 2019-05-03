package com.skydoves.githubfollows.utils

import android.content.Context
import android.graphics.drawable.PictureDrawable
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

/**
 * Developed by skydoves on 2018-02-18.
 * Copyright (c) 2018 skydoves rights reserved.
 */

class GlideUtils {
  companion object {
    fun getSvgRequestBuilder(context: Context): RequestBuilder<PictureDrawable> {
      return GlideApp.with(context)
          .`as`(PictureDrawable::class.java)
          .transition(DrawableTransitionOptions.withCrossFade())
          .listener(SvgSoftwareLayerSetter())
    }
  }
}

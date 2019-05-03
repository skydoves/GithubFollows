package com.skydoves.githubfollows.utils

import android.content.Context
import android.graphics.drawable.PictureDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule
import com.caverock.androidsvg.SVG
import java.io.InputStream

/**
 * Developed by skydoves on 2018-01-28.
 * Copyright (c) 2018 skydoves rights reserved.
 */

@GlideModule
class SvgModule : AppGlideModule() {
  override fun registerComponents(
    context: Context,
    glide: Glide,
    registry: Registry
  ) {
    registry.register(SVG::class.java, PictureDrawable::class.java, SvgDrawableTranscoder())
        .append(InputStream::class.java, SVG::class.java, SvgDecoder())
  }

  override fun isManifestParsingEnabled(): Boolean {
    return false
  }
}

/*
 * The MIT License (MIT)
 *
 * Designed and developed by 2018 skydoves (Jaewoong Eum)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.skydoves.githubfollows.utils

import android.graphics.drawable.PictureDrawable
import android.widget.ImageView
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.ImageViewTarget
import com.bumptech.glide.request.target.Target

/**
 * Listener which updates the [ImageView] to be software rendered, because
 * [SVG][com.caverock.androidsvg.SVG]/[Picture][android.graphics.Picture] can't render on
 * a hardware backed [Canvas][android.graphics.Canvas].
 */
class SvgSoftwareLayerSetter : RequestListener<PictureDrawable> {

  override fun onLoadFailed(
    e: GlideException?,
    model: Any,
    target: Target<PictureDrawable>,
    isFirstResource: Boolean
  ): Boolean {
    val view = (target as ImageViewTarget<*>).view
    view.scaleType = ImageView.ScaleType.FIT_XY
    view.setLayerType(ImageView.LAYER_TYPE_NONE, null)
    return false
  }

  override fun onResourceReady(
    resource: PictureDrawable,
    model: Any,
    target: Target<PictureDrawable>,
    dataSource: DataSource,
    isFirstResource: Boolean
  ): Boolean {
    val view = (target as ImageViewTarget<*>).view
    view.scaleType = ImageView.ScaleType.FIT_XY
    view.setLayerType(ImageView.LAYER_TYPE_SOFTWARE, null)
    return false
  }
}

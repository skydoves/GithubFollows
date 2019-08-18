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
@file:Suppress("unused")

package com.skydoves.githubfollows.extension

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.app.Activity
import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.view.View
import android.view.ViewAnimationUtils
import android.view.animation.AccelerateInterpolator
import androidx.core.content.ContextCompat
import com.skydoves.githubfollows.R

/**
 * Developed by skydoves on 2018-01-19.
 * Copyright (c) 2018 skydoves rights reserved.
 */

fun Activity.checkIsMaterialVersion() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP

fun Activity.circularRevealed(view: View, x: Int, y: Int) {
  if (checkIsMaterialVersion()) {
    val finalRadius = (Math.max(view.width, view.height) * 1.1)
    val circularReveal = ViewAnimationUtils.createCircularReveal(view, x, y, 0f, finalRadius.toFloat())
    circularReveal.duration = 400
    circularReveal.interpolator = AccelerateInterpolator()

    view.setBackgroundColor(ContextCompat.getColor(this, R.color.background800))
    view.visible()
    circularReveal.start()
  }
}

fun Activity.circularUnRevealed(mView: View, revealX: Int, revealY: Int) {
  if (checkIsMaterialVersion()) {
    val finalRadius = (Math.max(mView.width, mView.height) * 1.1)
    val circularReveal = ViewAnimationUtils.createCircularReveal(
      mView, revealX, revealY, finalRadius.toFloat(), 0f)

    circularReveal.duration = 350
    circularReveal.addListener(object : AnimatorListenerAdapter() {
      override fun onAnimationEnd(animation: Animator) {
        mView.inVisible()
        finish()
        overridePendingTransition(0, 0)
      }
    })

    circularReveal.start()
  }
}

fun Activity.fromResource(context: Context, layout: Int): Drawable {
  return ContextCompat.getDrawable(context, layout)!!
}

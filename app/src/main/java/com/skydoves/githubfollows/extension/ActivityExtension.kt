package com.skydoves.githubfollows.extension

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.app.Activity
import android.os.Build
import android.support.v4.content.ContextCompat
import android.view.View
import android.view.ViewAnimationUtils
import android.view.animation.AccelerateInterpolator
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
        view.visibility = View.VISIBLE
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
                mView.visibility = View.INVISIBLE
                finish()
                overridePendingTransition(0, 0)
            }
        })

        circularReveal.start()
    }
}

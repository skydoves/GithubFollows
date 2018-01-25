package com.skydoves.githubfollows.ui.activity.search

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver

import com.skydoves.githubfollows.R
import com.skydoves.githubfollows.extension.checkIsMaterialVersion
import com.skydoves.githubfollows.extension.circularRevealed
import com.skydoves.githubfollows.extension.circularUnRevealed
import kotlinx.android.synthetic.main.activity_search.*

/**
 * Developed by skydoves on 2018-01-22.
 * Copyright (c) 2018 skydoves rights reserved.
 */

class SearchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        if (savedInstanceState == null && checkIsMaterialVersion()) {
            search_layout.visibility = View.INVISIBLE

            val viewTreeObserver = search_layout.viewTreeObserver
            if (viewTreeObserver.isAlive) {
                viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
                    override fun onGlobalLayout() {
                        circularRevealed(search_layout ,search_layout.width, 0)
                        search_layout.viewTreeObserver.removeOnGlobalLayoutListener(this)
                    }
                })
            }
        }
    }

    override fun onBackPressed() {
        when(checkIsMaterialVersion()) {
            true -> circularUnRevealed(search_layout , search_layout.width, 0)
            false -> super.onBackPressed()
        }
    }
}

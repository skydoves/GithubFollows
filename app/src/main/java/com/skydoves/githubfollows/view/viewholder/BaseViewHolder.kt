package com.skydoves.githubfollows.view.viewholder

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.View

/**
 * Developed by skydoves on 2018-01-21.
 * Copyright (c) 2018 skydoves rights reserved.
 */

abstract class BaseViewHolder(private val view: View) : RecyclerView.ViewHolder(view), View.OnClickListener, View.OnLongClickListener {

    init {
        view.setOnClickListener(this)
        view.setOnLongClickListener(this)
    }

    @Throws(Exception::class)
    abstract fun bindData(data: Any)

    protected fun view(): View {
        return view
    }

    protected fun context(): Context {
        return view.context
    }
}
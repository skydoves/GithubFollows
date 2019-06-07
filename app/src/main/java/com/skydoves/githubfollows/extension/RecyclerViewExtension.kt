package com.skydoves.githubfollows.extension

import androidx.recyclerview.widget.RecyclerView
import com.skydoves.githubfollows.models.Resource
import com.skydoves.githubfollows.models.Status
import org.jetbrains.anko.toast

/**
 * Developed by skydoves on 2019-06-07.
 * Copyright (c) 2018 skydoves rights reserved.
 */

fun RecyclerView.bindResource(resource: Resource<Any>, onSuccess: () -> Unit) {
  when (resource.status) {
    Status.LOADING -> Unit
    Status.SUCCESS -> onSuccess()
    Status.ERROR -> this.context.toast(resource.message.toString())
  }
}

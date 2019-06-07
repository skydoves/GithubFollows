package com.skydoves.githubfollows.binding

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.skydoves.githubfollows.extension.bindResource
import com.skydoves.githubfollows.models.Follower
import com.skydoves.githubfollows.models.GithubUser
import com.skydoves.githubfollows.models.History
import com.skydoves.githubfollows.models.Resource
import com.skydoves.githubfollows.view.adapter.GithubUserAdapter
import com.skydoves.githubfollows.view.adapter.HistoryAdapter
import com.skydoves.githubfollows.view.ui.main.MainActivityViewModel

/**
 * Developed by skydoves on 2019-06-07.
 * Copyright (c) 2018 skydoves rights reserved.
 */

@BindingAdapter("adapterGithubUser")
fun bindAdapterGithubUser(view: RecyclerView, resource: Resource<GithubUser>?) {
  if (resource != null) {
    view.bindResource(resource) {
      val adapter = view.adapter as? GithubUserAdapter
      adapter?.updateHeader(resource)
    }
  }
}

@BindingAdapter("adapterFollowers", "viewModel")
fun bindAdapterFollowers(view: RecyclerView, resource: Resource<List<Follower>>?, viewModel: MainActivityViewModel) {
  if (resource != null) {
    viewModel.fetchStatus(resource)
    view.bindResource(resource) {
      val adapter = view.adapter as? GithubUserAdapter
      adapter?.addFollowList(resource.data!!)
    }
  }
}

@BindingAdapter("adapterHistory")
fun bindAdapterHistory(view: RecyclerView, resource: List<History>?) {
  if (resource != null) {
    val adapter = view.adapter as? HistoryAdapter
    adapter?.updateItemList(resource)
  }
}

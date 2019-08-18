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

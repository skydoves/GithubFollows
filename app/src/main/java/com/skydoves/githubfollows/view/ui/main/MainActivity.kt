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
package com.skydoves.githubfollows.view.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.skydoves.baserecyclerviewadapter.RecyclerViewPaginator
import com.skydoves.githubfollows.R
import com.skydoves.githubfollows.databinding.ActivityMainBinding
import com.skydoves.githubfollows.extension.vm
import com.skydoves.githubfollows.factory.AppViewModelFactory
import com.skydoves.githubfollows.factory.FollowMenuFactory
import com.skydoves.githubfollows.models.Follower
import com.skydoves.githubfollows.models.GithubUser
import com.skydoves.githubfollows.view.adapter.GithubUserAdapter
import com.skydoves.githubfollows.view.ui.detail.DetailActivity
import com.skydoves.githubfollows.view.ui.search.SearchActivity
import com.skydoves.githubfollows.view.viewholder.GithubUserHeaderViewHolder
import com.skydoves.githubfollows.view.viewholder.GithubUserViewHolder
import com.skydoves.powermenu.OnMenuItemClickListener
import com.skydoves.powermenu.PowerMenuItem
import com.skydoves.powermenu.kotlin.powerMenu
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar_main.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.startActivityForResult
import javax.inject.Inject

/**
 * Developed by skydoves on 2018-01-19.
 * Copyright (c) 2018 skydoves rights reserved.
 */

@Suppress("SpellCheckingInspection")
class MainActivity : AppCompatActivity(),
  GithubUserHeaderViewHolder.Delegate,
  GithubUserViewHolder.Delegate {

  @Inject
  lateinit var viewModelFactory: AppViewModelFactory
  private val viewModel by lazy { vm(viewModelFactory, MainActivityViewModel::class) }
  private lateinit var binding: ActivityMainBinding
  private lateinit var paginator: RecyclerViewPaginator
  private val adapter by lazy { GithubUserAdapter(this, this) }
  private val followMenu by powerMenu(FollowMenuFactory::class)

  private val onPowerMenuItemClickListener = OnMenuItemClickListener<PowerMenuItem> { position, item ->
    if (!item.isSelected) {
      viewModel.putPreferenceMenuPosition(position)
      followMenu.selectedPosition = position
      followMenu.dismiss()
      restPagination(viewModel.getUserName())
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    AndroidInjection.inject(this)
    super.onCreate(savedInstanceState)
    binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
    binding.viewModel = viewModel
    binding.lifecycleOwner = this

    main_recyclerView.adapter = adapter
    main_recyclerView.layoutManager = LinearLayoutManager(this)
    paginator = RecyclerViewPaginator(
      recyclerView = main_recyclerView,
      isLoading = { viewModel.fetchStatus.isOnLoading },
      loadMore = { loadMore(it) },
      onLast = { viewModel.fetchStatus.isOnLast }
    )

    initializeUI()
  }

  private fun initializeUI() {
    followMenu.onMenuItemClickListener = onPowerMenuItemClickListener
    followMenu.selectedPosition = viewModel.getPreferenceMenuPosition()
    toolbar_main_overflow.setOnClickListener { followMenu.showAsDropDown(it) }
    toolbar_main_search.setOnClickListener { startActivityForResult<SearchActivity>(SearchActivity.intent_requestCode) }
  }

  private fun loadMore(page: Int) {
    viewModel.postPage(page)
  }

  private fun restPagination(user: String) {
    adapter.clearAll()
    paginator.resetCurrentPage()
    viewModel.refresh(user)
  }

  override fun onCardClicked(githubUser: GithubUser) {
    startActivity<DetailActivity>(DetailActivity.intent_login to githubUser.login, DetailActivity.intent_avatar to githubUser.avatar_url)
  }

  override fun onItemClick(githubUser: Follower, view: View) {
    DetailActivity.startActivity(this, githubUser, view)
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    when (resultCode) {
      DetailActivity.intent_requestCode, SearchActivity.intent_requestCode -> data?.let {
        restPagination(data.getStringExtra(viewModel.getUserKeyName()))
      }
    }
  }

  override fun onBackPressed() {
    when (followMenu.isShowing) {
      true -> followMenu.dismiss()
      else -> super.onBackPressed()
    }
  }
}

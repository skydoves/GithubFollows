package com.skydoves.githubfollows.view.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.skydoves.baserecyclerviewadapter.RecyclerViewPaginator
import com.skydoves.githubfollows.R
import com.skydoves.githubfollows.extension.observeLiveData
import com.skydoves.githubfollows.factory.AppViewModelFactory
import com.skydoves.githubfollows.models.Follower
import com.skydoves.githubfollows.models.GithubUser
import com.skydoves.githubfollows.models.Resource
import com.skydoves.githubfollows.models.Status
import com.skydoves.githubfollows.utils.PowerMenuUtils
import com.skydoves.githubfollows.view.adapter.GithubUserAdapter
import com.skydoves.githubfollows.view.ui.detail.DetailActivity
import com.skydoves.githubfollows.view.ui.search.SearchActivity
import com.skydoves.githubfollows.view.viewholder.GithubUserHeaderViewHolder
import com.skydoves.githubfollows.view.viewholder.GithubUserViewHolder
import com.skydoves.powermenu.OnMenuItemClickListener
import com.skydoves.powermenu.PowerMenu
import com.skydoves.powermenu.PowerMenuItem
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar_main.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.startActivityForResult
import org.jetbrains.anko.toast
import javax.inject.Inject

/**
 * Developed by skydoves on 2018-01-19.
 * Copyright (c) 2018 skydoves rights reserved.
 */

class MainActivity : AppCompatActivity(),
    GithubUserHeaderViewHolder.Delegate,
    GithubUserViewHolder.Delegate {

  @Inject
  lateinit var viewModelFactory: AppViewModelFactory

  private val viewModel by lazy { ViewModelProviders.of(this, viewModelFactory).get(MainActivityViewModel::class.java) }
  private val adapter by lazy { GithubUserAdapter(this, this) }

  private lateinit var paginator: RecyclerViewPaginator
  private lateinit var powerMenu: PowerMenu

  private val onPowerMenuItemClickListener = OnMenuItemClickListener<PowerMenuItem> { position, item ->
    if (!item.isSelected) {
      viewModel.putPreferenceMenuPosition(position)
      powerMenu.selectedPosition = position
      powerMenu.dismiss()
      restPagination(viewModel.getUserName())
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    AndroidInjection.inject(this)
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    main_recyclerView.adapter = adapter
    main_recyclerView.layoutManager = LinearLayoutManager(this)
    paginator = RecyclerViewPaginator(
        recyclerView = main_recyclerView,
        isLoading = { viewModel.fetchStatus.isOnLoading },
        loadMore = { loadMore(it) },
        onLast = { viewModel.fetchStatus.isOnLast }
    )

    initializeUI()
    observeViewModel()
  }

  private fun initializeUI() {
    powerMenu = PowerMenuUtils.getOverflowPowerMenu(this, this, onPowerMenuItemClickListener)
    powerMenu.selectedPosition = viewModel.getPreferenceMenuPosition()
    toolbar_main_overflow.setOnClickListener { powerMenu.showAsDropDown(it) }
    toolbar_main_search.setOnClickListener { startActivityForResult<SearchActivity>(SearchActivity.intent_requestCode) }
  }

  private fun observeViewModel() {
    observeLiveData(viewModel.githubUserLiveData) { updateGithubUser(it) }
    observeLiveData(viewModel.followersLiveData) { updateFollowerList(it) }
  }

  private fun loadMore(page: Int) {
    viewModel.postPage(page)
  }

  private fun updateGithubUser(resource: Resource<GithubUser>) {
    when (resource.status) {
      Status.SUCCESS -> adapter.updateHeader(resource)
      Status.ERROR -> toast(resource.message.toString())
      Status.LOADING -> Unit
    }
  }

  private fun updateFollowerList(resource: Resource<List<Follower>>) {
    viewModel.fetchStatus(resource)
    when (resource.status) {
      Status.SUCCESS -> adapter.addFollowList(resource.data!!)
      Status.ERROR -> toast(resource.message.toString())
      Status.LOADING -> Unit
    }
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
    when (powerMenu.isShowing) {
      true -> powerMenu.dismiss()
      else -> super.onBackPressed()
    }
  }
}

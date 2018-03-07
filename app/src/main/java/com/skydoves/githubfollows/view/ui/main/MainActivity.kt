package com.skydoves.githubfollows.view.ui.main

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.skydoves.githubfollows.R
import com.skydoves.githubfollows.factory.AppViewModelFactory
import com.skydoves.githubfollows.models.Follower
import com.skydoves.githubfollows.models.GithubUser
import com.skydoves.githubfollows.utils.PowerMenuUtils
import com.skydoves.githubfollows.view.RecyclerViewPaginator
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

class MainActivity : AppCompatActivity(), GithubUserHeaderViewHolder.Delegate, GithubUserViewHolder.Delegate {

    @Inject lateinit var viewModelFactory: AppViewModelFactory

    private val viewModel by lazy { ViewModelProviders.of(this, viewModelFactory).get(MainActivityViewModel::class.java) }
    private val adapter by lazy { GithubUserAdapter(this, this) }

    private lateinit var paginator: RecyclerViewPaginator
    private lateinit var powerMenu: PowerMenu

    private val onPowerMenuItemClickListener = OnMenuItemClickListener<PowerMenuItem> { position, item ->
        if(!item.isSelected) {
            viewModel.putPreferenceMenuPosition(position)
            powerMenu.setSelected(position)
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
                isLoading = { viewModel.isLoading },
                loadMore = { loadMore(it) },
                onLast = { viewModel.isOnLast }
        )

        initializeUI()
        observeViewModel()
    }

    private fun initializeUI() {
        powerMenu = PowerMenuUtils.getOverflowPowerMenu(this, this, onPowerMenuItemClickListener)
        powerMenu.setSelected(viewModel.getPreferenceMenuPosition())
        toolbar_main_overflow.setOnClickListener { powerMenu.showAsDropDown(it) }
        toolbar_main_search.setOnClickListener { startActivityForResult<SearchActivity>(SearchActivity.intent_requestCode) }
    }

    private fun observeViewModel() {
        viewModel.githubUserLiveData.observe(this, Observer { it?.let { adapter.updateHeader(it) } })
        viewModel.githubUserListLiveData.observe(this, Observer { updateGithubUserList(it) })
        viewModel.toastMessage.observe(this, Observer { toast(it.toString()) })
        viewModel.getRepositoryToast().observe(this, Observer { toast(it.toString()) })
        loadMore(1)
    }

    private fun loadMore(page: Int) {
        when(viewModel.getPreferenceMenuPosition()) {
            0 -> viewModel.fetchFollowing(viewModel.getUserName(), page)
            1 -> viewModel.fetchFollowers(viewModel.getUserName(), page)
        }
    }

    private fun updateGithubUserList(followers: List<Follower>?) {
        followers?.let { adapter.addFollowList(it) }
    }

    private fun restPagination(user: String) {
        adapter.clearAll()
        viewModel.refresh(user)
        paginator.resetCurrentPage()
        loadMore(1)
    }

    override fun onCardClicked(githubUser: GithubUser) {
        startActivity<DetailActivity>(DetailActivity.intent_login to githubUser.login, DetailActivity.intent_avatar to githubUser.avatar_url)
    }

    override fun onItemClick(githubUser: Follower, view: View) {
        DetailActivity.startActivity(this, githubUser, view)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(resultCode) {
            DetailActivity.intent_requestCode, SearchActivity.intent_requestCode -> data?.let {
                restPagination(data.getStringExtra(viewModel.getUserKeyName()))
            }
        }
    }

    override fun onBackPressed() {
       when(powerMenu.isShowing) {
           true -> powerMenu.dismiss()
           else -> super.onBackPressed()
       }
    }
}
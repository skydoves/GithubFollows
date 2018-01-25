package com.skydoves.githubfollows.view.ui.main

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.skydoves.githubfollows.R
import com.skydoves.githubfollows.models.Follower
import com.skydoves.githubfollows.view.RecyclerViewPaginator
import com.skydoves.githubfollows.view.ui.search.SearchActivity
import com.skydoves.githubfollows.view.adapter.GithubUserAdapter
import com.skydoves.githubfollows.view.viewholder.GithubUserViewHolder
import com.skydoves.githubfollows.utils.PowerMenuUtils
import com.skydoves.githubfollows.factory.AppViewModelFactory
import com.skydoves.powermenu.OnMenuItemClickListener
import com.skydoves.powermenu.PowerMenu
import com.skydoves.powermenu.PowerMenuItem
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import javax.inject.Inject

/**
 * Developed by skydoves on 2018-01-19.
 * Copyright (c) 2018 skydoves rights reserved.
 */

class MainActivity : AppCompatActivity(), GithubUserViewHolder.Delegate {

    private val dummy = "skydoves"

    @Inject lateinit var viewModelFactory: AppViewModelFactory
    private val viewModel by lazy { ViewModelProviders.of(this, viewModelFactory).get(MainActivityViewModel::class.java) }

    private val adapter by lazy { GithubUserAdapter(this) }

    private lateinit var paginator: RecyclerViewPaginator
    private lateinit var powerMenu: PowerMenu

    private val onPowerMenuItemClickListener = OnMenuItemClickListener<PowerMenuItem> { position, item ->
        if(position != powerMenu.selectedPosition) {
            viewModel.resetPagination()
            paginator.resetCurrentPage()
            adapter.clearAll()

            powerMenu.setSelected(position)
            powerMenu.dismiss()

            loadMore(1)
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
        main_overflow.setOnClickListener { powerMenu.showAsDropDown(it) }
        main_search.setOnClickListener { startActivity<SearchActivity>() }
    }

    private fun observeViewModel() {
        viewModel.githubUserListLiveData.observe(this, Observer { updateGithubUserList(it) })
        viewModel.toastMessage.observe(this, Observer { toast(it.toString()) })
        loadMore(1)
    }

    private fun loadMore(page: Int) {
        when(powerMenu.selectedPosition) {
            -1, 0 -> viewModel.fetchFollowing(dummy, page)
            1 -> viewModel.fetchFollowers(dummy, page)
        }
    }

    private fun updateGithubUserList(followers: List<Follower>?) {
        followers?.let {
            adapter.addFollowList(it)
        }
    }

    override fun onItemClick(follower: Follower) {
        toast(follower.login)
    }

    override fun onBackPressed() {
        if(powerMenu.isShowing)
            powerMenu.dismiss()
        else
            super.onBackPressed()
    }
}
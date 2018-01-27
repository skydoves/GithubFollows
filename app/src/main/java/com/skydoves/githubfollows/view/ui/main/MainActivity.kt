package com.skydoves.githubfollows.view.ui.main

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.view.ViewCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.skydoves.githubfollows.R
import com.skydoves.githubfollows.extension.checkIsMaterialVersion
import com.skydoves.githubfollows.factory.AppViewModelFactory
import com.skydoves.githubfollows.models.Follower
import com.skydoves.githubfollows.utils.PowerMenuUtils
import com.skydoves.githubfollows.view.RecyclerViewPaginator
import com.skydoves.githubfollows.view.adapter.GithubUserAdapter
import com.skydoves.githubfollows.view.ui.detail.DetailActivity
import com.skydoves.githubfollows.view.ui.search.SearchActivity
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

class MainActivity : AppCompatActivity(), GithubUserViewHolder.Delegate {

    private lateinit var dummy: String

    @Inject lateinit var viewModelFactory: AppViewModelFactory

    private val viewModel by lazy { ViewModelProviders.of(this, viewModelFactory).get(MainActivityViewModel::class.java) }
    private val adapter by lazy { GithubUserAdapter(this) }

    private lateinit var paginator: RecyclerViewPaginator
    private lateinit var powerMenu: PowerMenu

    private val onPowerMenuItemClickListener = OnMenuItemClickListener<PowerMenuItem> { position, item ->
        if(!item.isSelected) {
            powerMenu.setSelected(position)
            powerMenu.dismiss()
            restPagination()
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
        toolbar_main_overflow.setOnClickListener { powerMenu.showAsDropDown(it) }
        toolbar_main_search.setOnClickListener { startActivityForResult<SearchActivity>(1000) }
    }

    private fun observeViewModel() {
        dummy = viewModel.getPreferenceUserName()
        viewModel.githubUserListLiveData.observe(this, Observer { updateGithubUserList(it) })
        viewModel.toastMessage.observe(this, Observer { toast(it.toString()) })
        loadMore(1)
    }

    private fun loadMore(page: Int) {
        when(powerMenu.selectedPosition) {
            1 -> viewModel.fetchFollowers(dummy, page)
            else -> viewModel.fetchFollowing(dummy, page)
        }
    }

    private fun updateGithubUserList(followers: List<Follower>?) {
        followers?.let { adapter.addFollowList(it) }
    }

    private fun restPagination() {
        viewModel.resetPagination()
        paginator.resetCurrentPage()
        adapter.clearAll()
        loadMore(1)
    }

    override fun onItemClick(follower: Follower, view: View) {
        if (checkIsMaterialVersion()) {
            val intent = Intent(this, DetailActivity::class.java)
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, view,
                    ViewCompat.getTransitionName(view))
            intent.putExtra("follower", follower)
            startActivityForResult(intent, 1000, options.toBundle())
        } else {
            startActivityForResult<DetailActivity>(1000, "follower" to follower)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(resultCode) {
            1000 -> data?.let {
                dummy = it.getStringExtra(viewModel.getPreferenceUserKeyName())
                restPagination()
            }
        }
    }

    override fun onBackPressed() {
        if(powerMenu.isShowing)
            powerMenu.dismiss()
        else
            super.onBackPressed()
    }
}
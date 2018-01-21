package com.skydoves.githubfollows.ui.activity.main

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.skydoves.githubfollows.R
import com.skydoves.githubfollows.models.Follower
import com.skydoves.githubfollows.ui.RecyclerViewpaginator
import com.skydoves.githubfollows.ui.adapter.GithubUserAdapter
import com.skydoves.githubfollows.ui.viewholder.GithubUserViewHolder
import com.skydoves.githubfollows.viewmodel.AppViewModelFactory
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast
import javax.inject.Inject

/**
 * Developed by skydoves on 2018-01-19.
 * Copyright (c) 2018 skydoves rights reserved.
 */

class MainActivity : AppCompatActivity(), GithubUserViewHolder.Delegate {

    @Inject
    lateinit var viewModelFactory: AppViewModelFactory

    private val viewModel by lazy { ViewModelProviders.of(this, viewModelFactory).get(MainActivityViewModel::class.java) }

    private val adapter by lazy { GithubUserAdapter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        main_recyclerView.adapter = adapter
        main_recyclerView.layoutManager = LinearLayoutManager(this)
        RecyclerViewpaginator(main_recyclerView,
                isLoading = { viewModel.isLoading },
                loadMore = { loadMore(it) },
                onLast = { viewModel.isOnLast }
        )

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.githubUserListLiveData.observe(this, Observer { updateGithubUserList(it) })
        viewModel.toastMessage.observe(this, Observer { toast(it.toString()) })

        loadMore(1)
    }

    private fun loadMore(page: Int) {
        viewModel.fetchFollowing("skydoves", page)
    }

    private fun updateGithubUserList(githubUserList: List<Follower>?) {
        githubUserList?.let {
            adapter.addGithubUserList(it)
        }
    }

    override fun onItemClick(githubUser: Follower) {

    }
}

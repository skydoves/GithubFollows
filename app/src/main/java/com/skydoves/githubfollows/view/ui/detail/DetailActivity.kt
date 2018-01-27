package com.skydoves.githubfollows.view.ui.detail

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.skydoves.githubfollows.R
import com.skydoves.githubfollows.factory.AppViewModelFactory
import com.skydoves.githubfollows.models.Follower
import com.skydoves.githubfollows.models.GithubUser
import com.skydoves.githubfollows.models.ItemDetail
import com.skydoves.githubfollows.view.adapter.DetailAdapter
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.layout_detail_body.*
import kotlinx.android.synthetic.main.layout_detail_header.*
import kotlinx.android.synthetic.main.toolbar_default.*
import org.jetbrains.anko.toast
import javax.inject.Inject

/**
 * Developed by skydoves on 2018-01-27.
 * Copyright (c) 2018 skydoves rights reserved.
 */

class DetailActivity : AppCompatActivity() {

    @Inject lateinit var viewModelFactory: AppViewModelFactory

    private val viewModel by lazy { ViewModelProviders.of(this, viewModelFactory).get(DetailActivityViewModel::class.java) }
    private val adapter by lazy { DetailAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        supportPostponeEnterTransition()

        initializeListeners()
        initializeUI()
    }

    private fun initializeListeners() {
        toolbar_home.setOnClickListener { onBackPressed() }
        detail_header_cardView.setOnClickListener {
            setResult(1000, Intent().putExtra(viewModel.getPreferenceUserKeyName(), getUserFromIntent().login))
            onBackPressed()
        }
    }

    private fun initializeUI() {
        val follower = getUserFromIntent()
        toolbar_title.text = follower.login
        Glide.with(this)
                .load(follower.avatar_url)
               .apply(RequestOptions().circleCrop().dontAnimate())
                .listener(object: RequestListener<Drawable> {
                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                        supportStartPostponedEnterTransition()
                        observeViewModel()
                        return false
                    }

                    override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                        supportStartPostponedEnterTransition()
                        observeViewModel()
                        return false
                    }
                })
                .into(detail_header_avatar)

        detail_body_recyclerView.layoutManager = LinearLayoutManager(this)
        detail_body_recyclerView.adapter = adapter
    }

    private fun observeViewModel() {
        viewModel.githubUserLiveData.observe(this, Observer { updateUI(it) })
        viewModel.toastMessage.observe(this, Observer { toast(it.toString()) })
        viewModel.fetchGithubUser(getUserFromIntent().login)
    }

    private fun updateUI(githubUser: GithubUser?) {
        githubUser?.let {
            detail_login.text = it.login
            when(it.name.isNullOrEmpty()) {
                true -> detail_name.text = it.login
                false -> detail_name.text = it.name
            }
            when(it.bio.isNullOrEmpty()) {
                true -> detail_bio.visibility = View.GONE
                false -> detail_bio.text = it.bio
            }
            adapter.addItemDetail(ItemDetail(R.drawable.ic_person_pin, it.html_url))
            it.company?.let { adapter.addItemDetail(ItemDetail(R.drawable.ic_people, it)) }
            it.location?.let { adapter.addItemDetail(ItemDetail(R.drawable.ic_location, it)) }
            it.blog?.let { adapter.addItemDetail(ItemDetail(R.drawable.ic_insert_link, it)) }
        }
    }

    private fun getUserFromIntent(): Follower {
        return intent.getParcelableExtra("follower")
    }
}

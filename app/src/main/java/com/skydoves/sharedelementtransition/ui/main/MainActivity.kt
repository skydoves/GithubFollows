package com.skydoves.sharedelementtransition.ui.main

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.skydoves.sharedelementtransition.R
import com.skydoves.sharedelementtransition.models.Following
import com.skydoves.sharedelementtransition.viewmodel.AppViewModelFactory
import dagger.android.AndroidInjection
import org.jetbrains.anko.toast
import javax.inject.Inject

/**
 * Developed by skydoves on 2018-01-19.
 * Copyright (c) 2018 skydoves rights reserved.
 */

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: AppViewModelFactory

    private val viewModel by lazy { ViewModelProviders.of(this, viewModelFactory).get(MainActivityViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.followingsLiveData.observe(this, Observer { updateFollowings(it) })
        viewModel.toastMessage.observe(this, Observer { toast(it.toString()) })

        viewModel.fetchFollowings("skydoves")
    }

    private fun updateFollowings(followings: List<Following>?) {
        followings?.let {

        }
    }
}

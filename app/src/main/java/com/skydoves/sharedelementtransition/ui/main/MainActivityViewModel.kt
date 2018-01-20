package com.skydoves.sharedelementtransition.ui.main

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.skydoves.sharedelementtransition.api.GithubService
import com.skydoves.sharedelementtransition.models.Following
import timber.log.Timber
import javax.inject.Inject

/**
 * Developed by skydoves on 2018-01-20.
 * Copyright (c) 2018 skydoves rights reserved.
 */

class MainActivityViewModel @Inject
constructor(private val service: GithubService): ViewModel() {

    val followingsLiveData: MutableLiveData<List<Following>> = MutableLiveData()
    val toastMessage: MutableLiveData<String> = MutableLiveData()

    init {
        Timber.d("Injection MainActivityViewModel")
    }

    fun fetchFollowings(user: String) {
        service.fetchFollowers(user).observeForever {
            it?.let {
                when(it.isSuccessful) {
                    true -> followingsLiveData.postValue(it.body)
                    false -> toastMessage.postValue(it.errorMessage)
                }
            }
        }
    }
}
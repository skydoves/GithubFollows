package com.skydoves.githubfollows.ui.activity.main

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.skydoves.githubfollows.api.GithubService
import com.skydoves.githubfollows.models.Follower
import timber.log.Timber
import javax.inject.Inject

/**
 * Developed by skydoves on 2018-01-20.
 * Copyright (c) 2018 skydoves rights reserved.
 */

class MainActivityViewModel @Inject
constructor(private val service: GithubService): ViewModel() {

    val githubUserListLiveData: MutableLiveData<List<Follower>> = MutableLiveData()
    val toastMessage: MutableLiveData<String> = MutableLiveData()

    var isLoading: Boolean = false
    var isOnLast: Boolean = false

    private val per_page = 10

    init {
        Timber.d("Injection MainActivityViewModel")
    }

    fun resetPagination() {
        isLoading = false
        isOnLast = false
    }

    fun fetchFollowing(user: String, page: Int) {
        isLoading = true
        service.fetchFollowings(user, page, per_page).observeForever {
            it?.let {
                when(it.isSuccessful) {
                    true -> githubUserListLiveData.postValue(it.body)
                    false -> toastMessage.postValue(it.errorMessage)
                }
                if(it.nextPage == null)
                    isOnLast = true
                isLoading = false
            }
        }
    }

    fun fetchFollowers(user: String, page: Int) {
        isLoading = true
        service.fetchFollowers(user, page, per_page).observeForever {
            it?.let {
                when(it.isSuccessful) {
                    true -> githubUserListLiveData.postValue(it.body)
                    false -> toastMessage.postValue(it.errorMessage)
                }
                isLoading = false
            }
        }
    }
}
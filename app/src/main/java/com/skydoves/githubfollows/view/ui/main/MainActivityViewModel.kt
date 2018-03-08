package com.skydoves.githubfollows.view.ui.main

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import com.skydoves.githubfollows.api.GithubService
import com.skydoves.githubfollows.models.Follower
import com.skydoves.githubfollows.models.GithubUser
import com.skydoves.githubfollows.models.Resource
import com.skydoves.githubfollows.models.Status
import com.skydoves.githubfollows.repository.GithubUserRepository
import com.skydoves.githubfollows.utils.AbsentLiveData
import timber.log.Timber
import javax.inject.Inject

/**
 * Developed by skydoves on 2018-01-20.
 * Copyright (c) 2018 skydoves rights reserved.
 */

class MainActivityViewModel @Inject
constructor(private val service: GithubService, private val githubUserRepository: GithubUserRepository): ViewModel() {

    private val login: MutableLiveData<String> = MutableLiveData()
    var githubUserLiveData: LiveData<Resource<GithubUser>> = MutableLiveData()

    val githubUserListLiveData: MutableLiveData<List<Follower>> = MutableLiveData()
    val toast: MutableLiveData<String> = MutableLiveData()

    var isLoading: Boolean = false
    var isOnLast: Boolean = false

    private val per_page = 10

    init {
        Timber.d("Injection MainActivityViewModel")

        login.postValue(getUserName())
        githubUserLiveData = Transformations.switchMap(login, {
            login.value?.let { githubUserRepository.loadUser(it) }
                    ?: AbsentLiveData.create()
        })

        githubUserLiveData.observeForever {
            if(it?.status == Status.ERROR) toast.postValue(it.message)
        }
    }

    fun refresh(user: String) {
        isLoading = false
        isOnLast = false
        login.postValue(user)
        githubUserRepository.refreshUser(user)
    }

    fun fetchFollowing(user: String, page: Int) {
        isLoading = true
        service.fetchFollowings(user, page, per_page).observeForever {
            it?.let {
                when(it.isSuccessful) {
                    true -> githubUserListLiveData.postValue(it.body)
                    false -> toast.postValue(it.envelope?.message)
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
                    false -> toast.postValue(it.envelope?.message)
                }
                isLoading = false
            }
        }
    }

    fun getPreferenceMenuPosition() = githubUserRepository.getPreferenceMenuPosition()

    fun putPreferenceMenuPosition(position: Int) = githubUserRepository.putPreferenceMenuPosition(position)

    fun getUserName() = githubUserRepository.getUserName()

    fun getUserKeyName() = githubUserRepository.getUserKeyName()
}
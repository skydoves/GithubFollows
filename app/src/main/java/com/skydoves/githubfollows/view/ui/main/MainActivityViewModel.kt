package com.skydoves.githubfollows.view.ui.main

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import com.skydoves.githubfollows.models.Follower
import com.skydoves.githubfollows.models.GithubUser
import com.skydoves.githubfollows.models.Resource
import com.skydoves.githubfollows.repository.GithubUserRepository
import com.skydoves.githubfollows.utils.AbsentLiveData
import timber.log.Timber
import javax.inject.Inject

/**
 * Developed by skydoves on 2018-01-20.
 * Copyright (c) 2018 skydoves rights reserved.
 */

class MainActivityViewModel @Inject
constructor(private val githubUserRepository: GithubUserRepository): ViewModel() {

    private val login: MutableLiveData<String> = MutableLiveData()
    private val page: MutableLiveData<Int> = MutableLiveData()
    private val isFollowers: MutableLiveData<Boolean> = MutableLiveData()

    var githubUserLiveData: LiveData<Resource<GithubUser>> = MutableLiveData()
    var followersLiveData: LiveData<Resource<List<Follower>>> = MutableLiveData()
    val toast: MutableLiveData<String> = MutableLiveData()

    var isLoading: Boolean = false
    var isOnLast: Boolean = false

    init {
        Timber.d("Injection MainActivityViewModel")

        login.postValue(getUserName())
        githubUserLiveData = Transformations.switchMap(login, {
            login.value?.let { githubUserRepository.loadUser(it) }
                    ?: AbsentLiveData.create()
        })

        githubUserLiveData.observeForever {
            it?.let { if(it.isOnError()) toast.postValue(it.message) }
        }

        isFollowers.postValue(isFollowers())
        followersLiveData = Transformations.switchMap(page, {
            login.value?.let {
                githubUserRepository.loadFollowers(it, page.value!!, isFollowers.value!!) }
            ?: AbsentLiveData.create()
        })

        followersLiveData .observeForever {
            it?.let {
                isLoading = it.isOnLoading()
                isOnLast = it.isOnLast() || it.isOnError()
                if(it.isOnError()) toast.postValue(it.message)
            }
        }
    }

    fun refresh(user: String) {
        isLoading = false
        isOnLast = false
        login.value = user
        isFollowers.value = isFollowers()
        githubUserRepository.refreshUser(user)
    }

    private fun isFollowers(): Boolean {
        if(getPreferenceMenuPosition() == 0) return false
        return true
    }

    fun postPage(page: Int) { this.page.value = page }

    fun getPreferenceMenuPosition() = githubUserRepository.getPreferenceMenuPosition()

    fun putPreferenceMenuPosition(position: Int) = githubUserRepository.putPreferenceMenuPosition(position)

    fun getUserName() = githubUserRepository.getUserName()

    fun getUserKeyName() = githubUserRepository.getUserKeyName()
}
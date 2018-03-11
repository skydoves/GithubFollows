package com.skydoves.githubfollows.view.ui.detail

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import com.skydoves.githubfollows.models.GithubUser
import com.skydoves.githubfollows.models.Resource
import com.skydoves.githubfollows.models.Status
import com.skydoves.githubfollows.repository.GithubUserRepository
import com.skydoves.githubfollows.utils.AbsentLiveData
import timber.log.Timber
import javax.inject.Inject

/**
 * Developed by skydoves on 2018-01-28.
 * Copyright (c) 2018 skydoves rights reserved.
 */

class DetailActivityViewModel @Inject
constructor(private val repository: GithubUserRepository): ViewModel() {

    private val login: MutableLiveData<String> = MutableLiveData()
    var githubUserLiveData: LiveData<Resource<GithubUser>> = MutableLiveData()
    val toast: MutableLiveData<String> = MutableLiveData()

    init {
        Timber.d("Injection DetailActivityViewModel")

        githubUserLiveData = Transformations.switchMap(login, {
            login.value?.let { repository.loadUser(it) }
                    ?: AbsentLiveData.create()
        })

        githubUserLiveData.observeForever {
            if(it?.status == Status.ERROR) toast.postValue(it.message)
        }
    }

    fun setUser(user: String) { login.value = user }

    fun getUserKeyName() = repository.getUserKeyName()
}

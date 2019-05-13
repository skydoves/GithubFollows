package com.skydoves.githubfollows.view.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.skydoves.githubfollows.models.GithubUser
import com.skydoves.githubfollows.models.Resource
import com.skydoves.githubfollows.repository.GithubUserRepository
import com.skydoves.githubfollows.utils.AbsentLiveData
import timber.log.Timber
import javax.inject.Inject

/**
 * Developed by skydoves on 2018-01-28.
 * Copyright (c) 2018 skydoves rights reserved.
 */

class DetailActivityViewModel @Inject
constructor(private val repository: GithubUserRepository) : ViewModel() {

  private val login: MutableLiveData<String> = MutableLiveData()
  val githubUserLiveData: LiveData<Resource<GithubUser>>

  init {
    Timber.d("Injection DetailActivityViewModel")

    githubUserLiveData = login.switchMap {
      login.value?.let { user -> repository.loadUser(user) }
          ?: AbsentLiveData.create()
    }
  }

  fun setUser(user: String) {
    login.value = user
  }

  fun getUserKeyName(): String = repository.getUserKeyName()
}

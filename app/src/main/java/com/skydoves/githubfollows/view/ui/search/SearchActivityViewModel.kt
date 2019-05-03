package com.skydoves.githubfollows.view.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.skydoves.githubfollows.models.GithubUser
import com.skydoves.githubfollows.models.History
import com.skydoves.githubfollows.models.Resource
import com.skydoves.githubfollows.repository.GithubUserRepository
import com.skydoves.githubfollows.repository.HistoryRepository
import com.skydoves.githubfollows.utils.AbsentLiveData
import timber.log.Timber
import javax.inject.Inject

/**
 * Developed by skydoves on 2018-01-27.
 * Copyright (c) 2018 skydoves rights reserved.
 */

class SearchActivityViewModel @Inject
constructor(
  private val githubUserRepository: GithubUserRepository,
  private val historyRepository: HistoryRepository
) : ViewModel() {

  val login: MutableLiveData<String> = MutableLiveData()
  val githubUserLiveData: LiveData<Resource<GithubUser>>

  init {
    Timber.d("Injection SearchActivityViewModel")

    githubUserLiveData = Transformations.switchMap(login) { user ->
      login.value?.let { githubUserRepository.loadUser(user) }
          ?: AbsentLiveData.create()
    }
  }

  fun insertHistory(search: String) = historyRepository.insertHistory(search)

  fun selectHistories(): LiveData<List<History>> = historyRepository.selectHistories()

  fun deleteHistory(history: History) = historyRepository.deleteHistory(history)

  fun getPreferenceUserKeyName(): String = githubUserRepository.getUserKeyName()
}

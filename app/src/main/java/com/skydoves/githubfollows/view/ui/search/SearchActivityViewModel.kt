package com.skydoves.githubfollows.view.ui.search

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
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
constructor(private val githubUserRepository: GithubUserRepository, private val historyRepository: HistoryRepository): ViewModel() {

    val login: MutableLiveData<String> = MutableLiveData()
    var githubUserLiveData: LiveData<Resource<GithubUser>> = MutableLiveData()
    val historiesLiveData: MutableLiveData<List<History>> = MutableLiveData()

    val toast: MutableLiveData<String> = MutableLiveData()

    init {
        Timber.d("Injection SearchActivityViewModel")

        githubUserLiveData = Transformations.switchMap(login, {
            login.value?.let { githubUserRepository.loadUser(it) }
                    ?: AbsentLiveData.create()
        })

        githubUserLiveData.observeForever {
            it?.let { if(it.isOnError()) toast.postValue(it.message) }
        }
    }

    fun insertHistory(search: String) = historyRepository.insertHistory(search)

    fun selectHistories() {
        historyRepository.selectHistories().observeForever {
            it?.let {
                if(it.isNotEmpty()) historiesLiveData.postValue(it)
            }
        }
    }

    fun deleteHistory(history: History) = historyRepository.deleteHistory(history)

    fun getPreferenceUserKeyName() = githubUserRepository.getUserKeyName()
}

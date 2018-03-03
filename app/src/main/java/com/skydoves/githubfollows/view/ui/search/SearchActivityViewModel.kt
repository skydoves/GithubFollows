package com.skydoves.githubfollows.view.ui.search

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.skydoves.githubfollows.api.GithubService
import com.skydoves.githubfollows.models.GithubUser
import com.skydoves.githubfollows.preference.PreferenceComponent_PrefAppComponent
import com.skydoves.githubfollows.preference.Preference_UserProfile
import com.skydoves.githubfollows.room.History
import com.skydoves.githubfollows.room.HistoryDao
import com.skydoves.preferenceroom.InjectPreference
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.util.*
import javax.inject.Inject

/**
 * Developed by skydoves on 2018-01-27.
 * Copyright (c) 2018 skydoves rights reserved.
 */

class SearchActivityViewModel @Inject
constructor(private val githubService: GithubService, private val historyDao: HistoryDao): ViewModel() {

    @InjectPreference lateinit var profile: Preference_UserProfile

    val githubUserLiveData: MutableLiveData<GithubUser> = MutableLiveData()
    val historiesLiveData: MutableLiveData<List<History>> = MutableLiveData()

    val toastMessage: MutableLiveData<String> = MutableLiveData()

    init {
        Timber.d("Injection SearchActivityViewModel")
        PreferenceComponent_PrefAppComponent.getInstance().inject(this)
    }

    fun fetchGithubUser(userName: String) {
        githubService.fetchGithubUser(userName).observeForever{
            it?.let {
                when(it.isSuccessful) {
                    true -> githubUserLiveData.postValue(it.body)
                    false -> toastMessage.postValue(it.envelope?.message)
                }
            }
        }
    }

    fun insertHistory(search: String) {
        Observable.just(historyDao)
                .subscribeOn(Schedulers.io())
                .subscribe { dao ->
                    dao.insertHistory(History(search, Calendar.getInstance().timeInMillis))
                    Timber.d("Dao insert history : $search")
                }
    }

    fun selectHistories() {
        historyDao.selectRecentHistoryList().observeForever {
            it?.let {
                if(it.isNotEmpty()) historiesLiveData.postValue(it)
            }
        }
    }

    fun deleteHistory(history: History) {
        Observable.just(historyDao)
                .subscribeOn(Schedulers.io())
                .subscribe { dao ->
                    dao.deleteHistory(history.search)
                    Timber.d("Dao delete history : ${history.search}")
                }
    }

    fun putPreferenceUserName(userName: String) {
        profile.putName(userName)
    }

    fun getPreferenceUserKeyName(): String {
        return profile.nameKeyName()
    }
}

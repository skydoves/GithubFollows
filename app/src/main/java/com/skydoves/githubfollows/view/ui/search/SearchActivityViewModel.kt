package com.skydoves.githubfollows.view.ui.search

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.skydoves.githubfollows.room.History
import com.skydoves.githubfollows.room.HistoryDao
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
constructor(private val historyDao: HistoryDao): ViewModel() {

    val historiesLiveData: MutableLiveData<List<History>> = MutableLiveData()

    init {
        Timber.d("Injection SearchActivityViewModel")
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
}

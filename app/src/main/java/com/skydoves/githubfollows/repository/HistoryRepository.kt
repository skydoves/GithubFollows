package com.skydoves.githubfollows.repository

import android.arch.lifecycle.LiveData
import com.skydoves.githubfollows.models.History
import com.skydoves.githubfollows.room.HistoryDao
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by skydoves on 2018. 3. 8.
 * Copyright (c) 2018 battleent All rights reserved.
 */

@Singleton
class HistoryRepository @Inject
constructor(private val historyDao: HistoryDao) {

    init {
        Timber.d("Injection HistoryRepository")
    }

    fun insertHistory(search: String) {
        Observable.just(historyDao)
                .subscribeOn(Schedulers.io())
                .subscribe { dao ->
                    dao.insertHistory(History(search, Calendar.getInstance().timeInMillis))
                    Timber.d("Dao insert history : $search")
                }
    }

    fun selectHistories(): LiveData<List<History>> {
        return historyDao.selectRecentHistoryList()
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

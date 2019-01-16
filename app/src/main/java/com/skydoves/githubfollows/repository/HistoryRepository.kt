package com.skydoves.githubfollows.repository

import androidx.lifecycle.LiveData
import com.skydoves.githubfollows.models.History
import com.skydoves.githubfollows.room.HistoryDao
import org.jetbrains.anko.doAsync
import timber.log.Timber
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by skydoves on 2018. 3. 8.
 * Copyright (c) 2018 skydoves All rights reserved.
 */

@Singleton
class HistoryRepository @Inject
constructor(private val historyDao: HistoryDao) {

    init {
        Timber.d("Injection HistoryRepository")
    }

    fun insertHistory(search: String) {
        doAsync {
            historyDao.insertHistory(History(search, Calendar.getInstance().timeInMillis))
            Timber.d("Dao insert history : $search")
        }
    }

    fun selectHistories(): LiveData<List<History>> {
        return historyDao.selectRecentHistoryList()
    }

    fun deleteHistory(history: History) {
        doAsync {
            historyDao.deleteHistory(history.search)
            Timber.d("Dao delete history : ${history.search}")
        }
    }
}

/*
 * The MIT License (MIT)
 *
 * Designed and developed by 2018 skydoves (Jaewoong Eum)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.skydoves.githubfollows.repository

import androidx.lifecycle.LiveData
import com.skydoves.githubfollows.models.History
import com.skydoves.githubfollows.room.HistoryDao
import org.jetbrains.anko.doAsync
import timber.log.Timber
import java.util.Calendar
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

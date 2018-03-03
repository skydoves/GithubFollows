package com.skydoves.githubfollows.persistence.db

import android.support.test.runner.AndroidJUnit4
import com.skydoves.githubfollows.room.History
import com.skydoves.githubfollows.utils.LiveDataTestUtil
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.notNullValue

import org.junit.Test
import org.junit.runner.RunWith
import java.util.*

/**
 * Developed by skydoves on 2018-03-03.
 * Copyright (c) 2018 skydoves rights reserved.
 */

@RunWith(AndroidJUnit4::class)
class HistoryDaoTest: DBTest() {

    private val test_search = "skydoves"
    private val test_history = Calendar.getInstance().timeInMillis

    @Test fun insertHistoryTest() {
        val history = History(test_search, test_history)
        db.historyDAO().insertHistory(history)

        val loaded = LiveDataTestUtil.getValue(db.historyDAO().selectRecentHistoryList())
        assertThat(loaded, notNullValue())
        assertThat(loaded[0].search, `is`(test_search))
        assertThat(loaded[0].history, `is`(test_history))
    }

    @Test fun deleteHistoryTest() {
        val history = History(test_search, test_history)
        db.historyDAO().insertHistory(history)
        db.historyDAO().deleteHistory(test_search)

        val loaded = LiveDataTestUtil.getValue(db.historyDAO().selectRecentHistoryList())
        assertThat(loaded.size, `is`(0))
    }
}

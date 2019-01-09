package com.skydoves.githubfollows.persistence.db

import android.support.test.runner.AndroidJUnit4
import com.skydoves.githubfollows.models.History
import com.skydoves.githubfollows.utils.LiveDataTestUtil
import com.skydoves.githubfollows.utils.MockTestUtil
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.notNullValue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Developed by skydoves on 2018-03-03.
 * Copyright (c) 2018 skydoves rights reserved.
 */

@RunWith(AndroidJUnit4::class)
class HistoryDaoTest : DBTest() {

    private lateinit var history: History
    private val search = "skydoves"

    @Before
    fun initMock() {
        history = MockTestUtil.mockHistory()
    }

    @Test
    fun insertHistoryTest() {
        db.historyDao().insertHistory(history)

        val loaded = LiveDataTestUtil.getValue(db.historyDao().selectRecentHistoryList())
        assertThat(loaded, notNullValue())
        assertThat(loaded[0].search, `is`(search))
        assertThat(loaded[0].history, `is`(MockTestUtil.mockTime))
    }

    @Test
    fun deleteHistoryTest() {
        db.historyDao().insertHistory(history)
        db.historyDao().deleteHistory(search)

        val loaded = LiveDataTestUtil.getValue(db.historyDao().selectRecentHistoryList())
        assertThat(loaded.size, `is`(0))
    }
}

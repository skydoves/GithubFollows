package com.skydoves.githubfollows.persistence.db

import android.support.test.runner.AndroidJUnit4
import com.skydoves.githubfollows.models.GithubUser
import com.skydoves.githubfollows.utils.LiveDataTestUtil
import com.skydoves.githubfollows.utils.MockTestUtil
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.notNullValue
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by skydoves on 2018. 3. 8.
 * Copyright (c) 2018 skydoves All rights reserved.
 */

@RunWith(AndroidJUnit4::class)
class GithubUserDaoTest : DBTest() {

    private lateinit var githubUser: GithubUser
    private val login = "skydoves"

    @Before fun initMock() {
        githubUser = MockTestUtil.mockGithubUser()
    }

    @Test fun insertGithubUsetTest() {
        db.githubUserDao().insertGithubUser(githubUser)

        val loaded = LiveDataTestUtil.getValue(db.githubUserDao().getGithubUser(login))
        assertThat(loaded, notNullValue())
        assertThat(loaded.login, `is`(login))
    }

    @Test fun truncateGithubUserTable() {
        db.githubUserDao().truncateGithubUser()

        val loaded = LiveDataTestUtil.getValue(db.githubUserDao().getGithubUser(login))
        Assert.assertNull(loaded)
    }
}

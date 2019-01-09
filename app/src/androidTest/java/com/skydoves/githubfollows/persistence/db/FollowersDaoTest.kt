package com.skydoves.githubfollows.persistence.db

import android.support.test.runner.AndroidJUnit4
import com.skydoves.githubfollows.models.Follower
import com.skydoves.githubfollows.utils.LiveDataTestUtil
import com.skydoves.githubfollows.utils.MockTestUtil
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by skydoves on 2018. 3. 13.
 * Copyright (c) 2018 skydoves All rights reserved.
 */

@RunWith(AndroidJUnit4::class)
class FollowersDaoTest : DBTest() {

    private lateinit var mockFollowerList: MutableList<Follower>
    private lateinit var follower: Follower
    private val owner = "skydoves"

    @Before
    fun initMock() {
        follower = MockTestUtil.mockFollower()
        mockFollowerList = ArrayList()
        mockFollowerList.add(follower)
        mockFollowerList.add(follower)
        mockFollowerList.add(follower)
    }

    @Test
    fun insertTest() {
        db.followersDao().insertFollowers(mockFollowerList)

        val loaded = LiveDataTestUtil.getValue(db.followersDao().getFollowers(owner, 1, false))
        assertThat(loaded.size, `is`(3))
        assertThat(loaded[0].owner, `is`(owner))
        assertThat(loaded[0].page, `is`(1))
        assertThat(loaded[0].isFollower, `is`(false))
    }
}

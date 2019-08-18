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
package com.skydoves.githubfollows.persistence.db

import androidx.test.runner.AndroidJUnit4
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

  @Before
  fun initMock() {
    githubUser = MockTestUtil.mockGithubUser()
  }

  @Test
  fun insertGithubUserTest() {
    db.githubUserDao().insertGithubUser(githubUser)

    val loaded = LiveDataTestUtil.getValue(db.githubUserDao().getGithubUser(login))
    assertThat(loaded, notNullValue())
    assertThat(loaded.login, `is`(login))
  }

  @Test
  fun truncateGithubUserTable() {
    db.githubUserDao().truncateGithubUser()

    val loaded = LiveDataTestUtil.getValue(db.githubUserDao().getGithubUser(login))
    Assert.assertNull(loaded)
  }
}

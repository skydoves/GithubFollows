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
package com.skydoves.githubfollows.utils

import com.skydoves.githubfollows.models.Follower
import com.skydoves.githubfollows.models.GithubUser
import com.skydoves.githubfollows.models.History
import java.util.Calendar

/**
 * Created by skydoves on 2018. 3. 8.
 * Copyright (c) 2018 skydoves All rights reserved.
 */

class MockTestUtil {
  companion object {

    val mockTime = Calendar.getInstance().timeInMillis

    fun mockHistory(): History {
      return History("skydoves", mockTime)
    }

    fun mockGithubUser(): GithubUser {
      return GithubUser("skydoves", 1000, "", "", "", "", "", "", "",
          "", "", "", "", "", "", "", false,
          null, null, null, null, null, false, null, 0, 0, 0,
          0, "", "")
    }

    fun mockFollower(): Follower {
      return Follower(0, "skydoves", 1000, "", "", "", "", "", "",
          "", "", "", "", "", "", "", "",
          false, "skydoves", 1, false)
    }
  }
}

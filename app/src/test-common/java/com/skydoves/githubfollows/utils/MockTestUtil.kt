package com.skydoves.githubfollows.utils

import com.skydoves.githubfollows.models.GithubUser
import com.skydoves.githubfollows.models.History
import java.util.*

/**
 * Created by skydoves on 2018. 3. 8.
 * Copyright (c) 2018 battleent All rights reserved.
 */

class MockTestUtil {
    companion object {

        val mockTime = Calendar.getInstance().timeInMillis

        fun mockHistory(): History {
            return History("skydoves", mockTime)
        }

        fun mockGithubUser(): GithubUser {
            return GithubUser("skydoves", 1000, "", "", "","", "", "","",
                    "", "","", "", "","", "", false,
                    null, null, null, null, null, null, null, 0, 0, 0,
                    0, "", "")
        }
    }
}

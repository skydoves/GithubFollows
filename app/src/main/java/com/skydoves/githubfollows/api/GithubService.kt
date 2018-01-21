package com.skydoves.githubfollows.api

import android.arch.lifecycle.LiveData
import com.skydoves.githubfollows.models.Following
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Developed by skydoves on 2018-01-20.
 * Copyright (c) 2018 skydoves rights reserved.
 */

interface GithubService {
    @GET("/users/{user}/following")
    fun fetchFollowers(@Path("user") user: String): LiveData<ApiResponse<List<Following>>>
}
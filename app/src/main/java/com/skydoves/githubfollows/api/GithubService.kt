package com.skydoves.githubfollows.api

import androidx.lifecycle.LiveData
import com.skydoves.githubfollows.models.Follower
import com.skydoves.githubfollows.models.GithubUser
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Developed by skydoves on 2018-01-20.
 * Copyright (c) 2018 skydoves rights reserved.
 */

interface GithubService {
    @GET("/users/{user}")
    fun fetchGithubUser(@Path("user") user: String): LiveData<ApiResponse<GithubUser>>

    @GET("/users/{user}/following")
    fun fetchFollowings(@Path("user") user: String, @Query("page") page: Int, @Query("per_page") per_page: Int): LiveData<ApiResponse<List<Follower>>>

    @GET("/users/{user}/followers")
    fun fetchFollowers(@Path("user") user: String, @Query("page") page: Int, @Query("per_page") per_page: Int): LiveData<ApiResponse<List<Follower>>>
}
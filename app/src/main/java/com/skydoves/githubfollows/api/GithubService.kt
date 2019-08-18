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
  /**
   * [Users](https://developer.github.com/v3/users/)
   *
   * Provides publicly available information about someone with a GitHub account.
   *
   * @param [user] the name of the user.
   *
   * @return [GithubUser] github user model.
   */
  @GET("/users/{user}")
  fun fetchGithubUser(@Path("user") user: String): LiveData<ApiResponse<GithubUser>>

  /**
   * [Followers](https://developer.github.com/v3/users/followers/#list-followers-of-a-user)
   *
   * List the authenticated user's followers.
   *
   * @param [user] the name of a user.
   * @param [page] the number of a page.
   * @param [per_page] the number of followers per page.
   *
   * @return [Follower] github user model.
   */
  @GET("/users/{user}/followers")
  fun fetchFollowers(@Path("user") user: String, @Query("page") page: Int, @Query("per_page") per_page: Int): LiveData<ApiResponse<List<Follower>>>

  /**
   * [Followings](https://developer.github.com/v3/users/followers/#check-if-you-are-following-a-user)
   *
   * List who a user is following.
   *
   * @param [user] the name of the user.
   * @param [page] the number of a page.
   * @param [per_page] the number of followers per page
   *
   * @return [Follower] github follower model.
   */
  @GET("/users/{user}/following")
  fun fetchFollowings(@Path("user") user: String, @Query("page") page: Int, @Query("per_page") per_page: Int): LiveData<ApiResponse<List<Follower>>>
}

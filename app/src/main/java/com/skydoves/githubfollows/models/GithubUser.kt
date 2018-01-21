package com.skydoves.githubfollows.models

/**
 * Developed by skydoves on 2018-01-21.
 * Copyright (c) 2018 skydoves rights reserved.
 */

data class GithubUser(val login: String,
                      val id: Int,
                      val avatar_url: String,
                      val gravatar_id: String,
                      val url: String,
                      val html_url: String,
                      val followers_url: String,
                      val following_url: String,
                      val gists_url: String,
                      val starred_url: String,
                      val subscriptions_url: String,
                      val organizations_url: String,
                      val repos_url: String,
                      val events_url: String,
                      val received_events_url: String,
                      val type: String,
                      val site_admin: Boolean,
                      val name: String,
                      val company: String,
                      val blog: String,
                      val location: String,
                      val email: String,
                      val hireable: String,
                      val bio: String,
                      val public_repos: Int,
                      val public_gists: Int,
                      val followers: Int,
                      val following: Int,
                      val created_at: String,
                      val updated_at: String)
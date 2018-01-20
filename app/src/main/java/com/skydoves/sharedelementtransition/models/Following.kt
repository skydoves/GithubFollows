package com.skydoves.sharedelementtransition.models

/**
 * Developed by skydoves on 2018-01-20.
 * Copyright (c) 2018 skydoves rights reserved.
 */

data class Following(val login: String,
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
                     val site_admin: Boolean)
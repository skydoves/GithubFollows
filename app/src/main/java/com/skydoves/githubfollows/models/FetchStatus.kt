package com.skydoves.githubfollows.models

/**
 * Developed by skydoves on 2018-03-18.
 * Copyright (c) 2018 skydoves rights reserved.
 */

data class FetchStatus(var isOnLoading: Boolean = false,
                       var isOnSuccess: Boolean = false,
                       var isOnError: Boolean = false,
                       var isOnLast: Boolean = false,
                       var nextPage: Int? = null)

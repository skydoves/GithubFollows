package com.skydoves.githubfollows.preference

import com.skydoves.githubfollows.repository.GithubUserRepository
import com.skydoves.githubfollows.view.ui.detail.DetailActivityViewModel
import com.skydoves.githubfollows.view.ui.search.SearchActivityViewModel
import com.skydoves.preferenceroom.PreferenceComponent

/**
 * Developed by skydoves on 2018-01-27.
 * Copyright (c) 2018 skydoves rights reserved.
 */

@PreferenceComponent(entities = [(Profile::class)])
interface PrefAppComponent {
  fun inject(target: SearchActivityViewModel)
  fun inject(target: DetailActivityViewModel)
  fun inject(target: GithubUserRepository)
}

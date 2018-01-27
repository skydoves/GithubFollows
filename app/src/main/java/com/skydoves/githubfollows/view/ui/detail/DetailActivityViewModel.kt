package com.skydoves.githubfollows.view.ui.detail

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.skydoves.githubfollows.api.GithubService
import com.skydoves.githubfollows.models.GithubUser
import com.skydoves.githubfollows.preference.PreferenceComponent_PrefAppComponent
import com.skydoves.githubfollows.preference.Preference_UserProfile
import com.skydoves.preferenceroom.InjectPreference
import timber.log.Timber
import javax.inject.Inject

/**
 * Developed by skydoves on 2018-01-28.
 * Copyright (c) 2018 skydoves rights reserved.
 */

class DetailActivityViewModel @Inject
constructor(private val githubService: GithubService): ViewModel() {

    @InjectPreference lateinit var profile: Preference_UserProfile

    val githubUserLiveData: MutableLiveData<GithubUser> = MutableLiveData()
    val toastMessage: MutableLiveData<String> = MutableLiveData()

    init {
        Timber.d("Injection DetailActivityViewModel")
        PreferenceComponent_PrefAppComponent.getInstance().inject(this)
    }

    fun fetchGithubUser(user: String) {
        githubService.fetchGithubUser(user).observeForever {
            it?.let {
                when(it.isSuccessful) {
                    true -> githubUserLiveData.postValue(it.body)
                    false -> toastMessage.postValue(it.envelope?.message)
                }
            }
        }
    }

    fun getPreferenceUserKeyName(): String {
        return profile.nameKeyName()
    }
}

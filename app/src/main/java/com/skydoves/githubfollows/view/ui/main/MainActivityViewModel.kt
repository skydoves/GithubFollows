package com.skydoves.githubfollows.view.ui.main

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.skydoves.githubfollows.api.GithubService
import com.skydoves.githubfollows.models.Follower
import com.skydoves.githubfollows.preference.PreferenceComponent_PrefAppComponent
import com.skydoves.githubfollows.preference.Preference_UserProfile
import com.skydoves.preferenceroom.InjectPreference
import timber.log.Timber
import javax.inject.Inject

/**
 * Developed by skydoves on 2018-01-20.
 * Copyright (c) 2018 skydoves rights reserved.
 */

class MainActivityViewModel @Inject
constructor(private val service: GithubService): ViewModel() {

    @InjectPreference lateinit var profile: Preference_UserProfile

    val githubUserListLiveData: MutableLiveData<List<Follower>> = MutableLiveData()
    val toastMessage: MutableLiveData<String> = MutableLiveData()

    var isLoading: Boolean = false
    var isOnLast: Boolean = false

    private val per_page = 10

    init {
        Timber.d("Injection MainActivityViewModel")
        PreferenceComponent_PrefAppComponent.getInstance().inject(this)
    }

    fun resetPagination() {
        isLoading = false
        isOnLast = false
    }

    fun fetchFollowing(user: String, page: Int) {
        isLoading = true
        service.fetchFollowings(user, page, per_page).observeForever {
            it?.let {
                when(it.isSuccessful) {
                    true -> githubUserListLiveData.postValue(it.body)
                    false -> toastMessage.postValue(it.envelope?.message)
                }
                if(it.nextPage == null)
                    isOnLast = true
                isLoading = false
            }
        }
    }

    fun fetchFollowers(user: String, page: Int) {
        isLoading = true
        service.fetchFollowers(user, page, per_page).observeForever {
            it?.let {
                when(it.isSuccessful) {
                    true -> githubUserListLiveData.postValue(it.body)
                    false -> toastMessage.postValue(it.envelope?.message)
                }
                isLoading = false
            }
        }
    }

    fun getPreferenceUserName(): String {
        return profile.name
    }

    fun getPreferenceUserKeyName(): String {
        return profile.nameKeyName()
    }

    fun getPreferenceMenuPosition(): Int {
        return profile.menuPosition
    }

    fun putPreferenceMenuPosition(position: Int) {
        profile.putMenuPosition(position)
    }
}
package com.skydoves.githubfollows.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.skydoves.githubfollows.api.ApiResponse
import com.skydoves.githubfollows.api.GithubService
import com.skydoves.githubfollows.models.Envelope
import com.skydoves.githubfollows.models.GithubUser
import com.skydoves.githubfollows.models.Resource
import com.skydoves.githubfollows.preference.PreferenceComponent_PrefAppComponent
import com.skydoves.githubfollows.preference.Preference_UserProfile
import com.skydoves.githubfollows.room.GithubUserDao
import com.skydoves.preferenceroom.InjectPreference
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

/**s
 * Created by skydoves on 2018. 3. 6.
 * Copyright (c) 2018 battleent All rights reserved.
 */

@Singleton
class GithubUserRepository @Inject
constructor(val githubUserDao: GithubUserDao, val service: GithubService) {

    @InjectPreference lateinit var profile: Preference_UserProfile

    val toast: MutableLiveData<String> = MutableLiveData()

    init {
        Timber.d("Injection GithubUserRepository")
        PreferenceComponent_PrefAppComponent.getInstance().inject(this)
    }

    fun refreshUser(user: String) {
        profile.putName(user)
        Observable.just(githubUserDao)
                .subscribeOn(Schedulers.io())
                .subscribe { dao ->
                    dao.truncateGithubUser()
                }
    }

    fun loadUser(user: String): LiveData<Resource<GithubUser>> {
        return object: NetworkBoundRepository<GithubUser, GithubUser>() {
            override fun saveFetchData(item: GithubUser) {
                Observable.just(githubUserDao)
                        .subscribeOn(Schedulers.io())
                        .subscribe { dao ->
                            dao.insertGithubUser(item)
                        }
            }

            override fun shouldFetch(data: GithubUser?): Boolean {
                return data == null
            }

            override fun loadFromDb(): LiveData<GithubUser> {
                return githubUserDao.getGithubUser(user)
            }

            override fun fetchService(): LiveData<ApiResponse<GithubUser>> {
                return service.fetchGithubUser(user)
            }

            override fun onFetchFailed(envelope: Envelope?) {
                toast.postValue(envelope?.message)
            }
        }.asLiveData()
    }

    fun getUserKeyName(): String {
        return profile.nameKeyName()
    }

    fun getPreferenceMenuPosition(): Int {
        return profile.menuPosition
    }

    fun putPreferenceMenuPosition(position: Int) {
        profile.putMenuPosition(position)
    }

    fun getUserName(): String {
        return profile.name
    }

    fun putPreferenceUser(user: String) {
        profile.putName(user)
    }
}

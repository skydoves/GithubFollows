package com.skydoves.githubfollows.api

import android.content.Context
import android.content.SharedPreferences
import androidx.arch.core.executor.ArchTaskExecutor
import androidx.arch.core.executor.TaskExecutor
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.nhaarman.mockitokotlin2.whenever
import com.skydoves.githubfollows.api.ApiResponseUtil.successCall
import com.skydoves.githubfollows.models.GithubUser
import com.skydoves.githubfollows.models.Resource
import com.skydoves.githubfollows.preference.PreferenceComponent_PrefAppComponent
import com.skydoves.githubfollows.repository.GithubUserRepository
import com.skydoves.githubfollows.room.FollowersDao
import com.skydoves.githubfollows.room.GithubUserDao
import com.skydoves.githubfollows.utils.MockTestUtil
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 * Developed by skydoves on 2019-01-23.
 * Copyright (c) 2018 skydoves rights reserved.
 */

@RunWith(JUnit4::class)
class GithubUserRepositoryTest {

    private lateinit var repository: GithubUserRepository
    private val githubUserDao = mock<GithubUserDao>()
    private val followersDao = mock<FollowersDao>()
    private val service = mock<GithubService>()

    private val user = "skydoves"

    @Before
    fun init() {
        val context = mock<Context>()
        val preference = mock<SharedPreferences>()
        whenever(context.applicationContext).thenReturn(context)
        whenever(context.getSharedPreferences("UserProfile", Context.MODE_PRIVATE)).thenReturn(preference)
        PreferenceComponent_PrefAppComponent.init(context)

        repository = GithubUserRepository(githubUserDao, followersDao, service)

        ArchTaskExecutor.getInstance().setDelegate(object : TaskExecutor() {
            override fun executeOnDiskIO(runnable: Runnable) = runnable.run()
            override fun isMainThread() = true
            override fun postToMainThread(runnable: Runnable) = runnable.run()
        })
    }

    @After
    fun terminate() {
        ArchTaskExecutor.getInstance().setDelegate(null)
    }

    @Test
    fun loadUserFromNetwork() {
        val loadFromDB = MutableLiveData<GithubUser>()
        whenever(githubUserDao.getGithubUser(user)).thenReturn(loadFromDB)

        val mockResponse = MockTestUtil.mockGithubUser()
        val call = successCall(mockResponse)
        whenever(service.fetchGithubUser(user)).thenReturn(call)

        val data = repository.loadUser(user)
        verify(githubUserDao).getGithubUser(user)
        verifyNoMoreInteractions(service)

        val observer = mock<Observer<Resource<GithubUser>>>()
        data.observeForever(observer)
        verifyNoMoreInteractions(service)
        val updateData = MutableLiveData<GithubUser>()
        whenever(githubUserDao.getGithubUser(user)).thenReturn(updateData)

        loadFromDB.postValue(null)
        verify(observer).onChanged(Resource.loading(null, 1))
        verify(service).fetchGithubUser(user)
        verify(githubUserDao, times(2)).getGithubUser(user)

        updateData.postValue(mockResponse)
        verify(observer).onChanged(Resource.success(mockResponse, 2))
    }
}
package com.skydoves.githubfollows.api

import androidx.arch.core.executor.ArchTaskExecutor
import androidx.arch.core.executor.TaskExecutor
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.skydoves.githubfollows.utils.LiveDataTestUtil
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import okio.Okio
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.nio.charset.StandardCharsets

/**
 * Created by skydoves on 2018. 3. 13.
 * Copyright (c) 2018 skydoves All rights reserved.
 */

@RunWith(JUnit4::class)
class GithubServiceTest {
    private lateinit var service: GithubService
    private lateinit var mockWebServer: MockWebServer

    @Throws(IOException::class)
    @Before
    fun createService() {
        mockWebServer = MockWebServer()
        service = Retrofit.Builder()
                .baseUrl(mockWebServer.url("/"))
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(LiveDataCallAdapterFactory())
                .build()
                .create(GithubService::class.java)

        ArchTaskExecutor.getInstance().setDelegate(object : TaskExecutor() {
            override fun executeOnDiskIO(runnable: Runnable) = runnable.run()
            override fun isMainThread() = true
            override fun postToMainThread(runnable: Runnable) = runnable.run()
        })
    }

    @Throws(IOException::class)
    @After
    fun stopService() {
        mockWebServer.shutdown()
        ArchTaskExecutor.getInstance().setDelegate(null)
    }

    @Test
    fun getGithubUserTest() {
        enqueueResponse("user-skydoves.json")
        val githubUser = LiveDataTestUtil.getValue(service.fetchGithubUser("skydoves")).body

        val request: RecordedRequest = mockWebServer.takeRequest()
        assertThat(request.path, `is`("/users/skydoves"))

        assertThat(githubUser, notNullValue())
        assertThat(githubUser?.login, `is`("skydoves"))
        assertThat(githubUser?.id, `is`(24237865))
        assertThat(githubUser?.avatar_url, `is`("https://avatars2.githubusercontent.com/u/24237865?v=4"))
        assertThat(githubUser?.url, `is`("https://api.github.com/users/skydoves"))
    }

    @Test
    fun getFollowingsTest() {
        enqueueResponse("following-skydoves.json")
        val followers = LiveDataTestUtil.getValue(service.fetchFollowings("skydoves", 1, 10)).body

        val request: RecordedRequest = mockWebServer.takeRequest()
        assertThat(request.path, `is`("/users/skydoves/following?page=1&per_page=10"))

        assertThat(followers, notNullValue())
        assertThat(followers?.get(0)?.login, `is`("JakeWharton"))
        assertThat(followers?.get(0)?.id, `is`(66577))
        assertThat(followers?.get(0)?.avatar_url, `is`("https://avatars0.githubusercontent.com/u/66577?v=4"))
        assertThat(followers?.get(0)?.url, `is`("https://api.github.com/users/JakeWharton"))
    }

    @Test
    fun getFollowersTest() {
        enqueueResponse("followers-skydoves.json")
        val followers = LiveDataTestUtil.getValue(service.fetchFollowers("skydoves", 1, 10)).body

        val request: RecordedRequest = mockWebServer.takeRequest()
        assertThat(request.path, `is`("/users/skydoves/followers?page=1&per_page=10"))

        assertThat(followers, notNullValue())
        assertThat(followers?.get(6)?.login, `is`("Seo-Hyung"))
        assertThat(followers?.get(6)?.id, `is`(18183191))
        assertThat(followers?.get(6)?.avatar_url, `is`("https://avatars3.githubusercontent.com/u/18183191?v=4"))
        assertThat(followers?.get(6)?.url, `is`("https://api.github.com/users/Seo-Hyung"))
    }

    @Throws(IOException::class)
    private fun enqueueResponse(fileName: String) {
        enqueueResponse(fileName, emptyMap())
    }

    @Throws(IOException::class)
    private fun enqueueResponse(fileName: String, headers: Map<String, String>) {
        val inputStream = javaClass.classLoader.getResourceAsStream("api-response/$fileName")
        val source = Okio.buffer(Okio.source(inputStream))
        val mockResponse = MockResponse()
        for ((key, value) in headers) {
            mockResponse.addHeader(key, value)
        }
        mockWebServer.enqueue(mockResponse.setBody(source.readString(StandardCharsets.UTF_8)))
    }
}

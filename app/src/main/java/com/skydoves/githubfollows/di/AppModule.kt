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
package com.skydoves.githubfollows.di

import android.app.Application
import androidx.room.Room
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.skydoves.githubfollows.api.GithubService
import com.skydoves.githubfollows.api.LiveDataCallAdapterFactory
import com.skydoves.githubfollows.room.AppDatabase
import com.skydoves.githubfollows.room.FollowersDao
import com.skydoves.githubfollows.room.GithubUserDao
import com.skydoves.githubfollows.room.HistoryDao
import dagger.Module
import dagger.Provides
import io.reactivex.annotations.NonNull
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Developed by skydoves on 2018-01-21.
 * Copyright (c) 2018 skydoves rights reserved.
 */

@Module
class AppModule {
  @Provides
  @Singleton
  fun provideOkHttpClient(): OkHttpClient {
    return OkHttpClient.Builder()
      .addNetworkInterceptor(StethoInterceptor())
      .build()
  }

  @Provides
  @Singleton
  fun provideRetrofit(@NonNull okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
      .client(okHttpClient)
      .baseUrl("https://api.github.com")
      .addConverterFactory(GsonConverterFactory.create())
      .addCallAdapterFactory(LiveDataCallAdapterFactory())
      .build()
  }

  @Provides
  @Singleton
  fun provideGithubService(@NonNull retrofit: Retrofit): GithubService {
    return retrofit.create(GithubService::class.java)
  }

  @Provides
  @Singleton
  fun provideDatabase(@NonNull application: Application): AppDatabase {
    return Room.databaseBuilder(
      application.applicationContext,
      AppDatabase::class.java,
      "GithubFollows.db")
      .allowMainThreadQueries()
      .build()
  }

  @Provides
  @Singleton
  fun provideHistoryDao(@NonNull database: AppDatabase): HistoryDao {
    return database.historyDao()
  }

  @Provides
  @Singleton
  fun provideGithubUserDao(@NonNull database: AppDatabase): GithubUserDao {
    return database.githubUserDao()
  }

  @Provides
  @Singleton
  fun provideFollowersDao(@NonNull database: AppDatabase): FollowersDao {
    return database.followersDao()
  }
}

package com.skydoves.githubfollows.di

import android.app.Application
import android.arch.persistence.room.Room
import com.skydoves.githubfollows.api.GithubService
import com.skydoves.githubfollows.api.LiveDataCallAdapterFactory
import com.skydoves.githubfollows.room.AppDatabase
import com.skydoves.githubfollows.room.FollowersDao
import com.skydoves.githubfollows.room.GithubUserDao
import com.skydoves.githubfollows.room.HistoryDao
import dagger.Module
import dagger.Provides
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
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
                .baseUrl("https://api.github.com")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(LiveDataCallAdapterFactory())
                .build()
    }

    @Provides
    @Singleton
    fun provideGithubService(retrofit: Retrofit): GithubService {
        return retrofit.create(GithubService::class.java)
    }

    @Provides
    @Singleton
    fun provideDatabase(application: Application): AppDatabase {
        return Room.databaseBuilder(application.applicationContext, AppDatabase::class.java,"GithubFollows.db").allowMainThreadQueries().build()
    }

    @Provides
    @Singleton
    fun provideHistoryDao(database: AppDatabase): HistoryDao {
        return database.historyDao()
    }

    @Provides
    @Singleton
    fun provideGithubUserDao(database: AppDatabase): GithubUserDao {
        return database.githubUserDao()
    }

    @Provides
    @Singleton
    fun provideFollowersDao(database: AppDatabase): FollowersDao {
        return database.followersDao()
    }
}

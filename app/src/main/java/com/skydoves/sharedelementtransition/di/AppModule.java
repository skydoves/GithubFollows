package com.skydoves.sharedelementtransition.di;

import com.skydoves.sharedelementtransition.api.GithubService;
import com.skydoves.sharedelementtransition.api.LiveDataCallAdapterFactory;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Developed by skydoves on 2018-01-21.
 * Copyright (c) 2018 skydoves rights reserved.
 */

@Module
public class AppModule {
    @Provides
    @Singleton
    public Retrofit provideRetrofit() {
        return new Retrofit.Builder()
                .baseUrl("https://api.github.com")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(new LiveDataCallAdapterFactory())
                .build();
    }

    @Provides
    @Singleton
    public GithubService provideGithubService(Retrofit retrofit) {
        return retrofit.create(GithubService.class);
    }
}

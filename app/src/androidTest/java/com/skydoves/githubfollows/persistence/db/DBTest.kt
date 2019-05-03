package com.skydoves.githubfollows.persistence.db

import androidx.room.Room
import androidx.test.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4

import com.skydoves.githubfollows.room.AppDatabase

import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith

/**
 * Developed by skydoves on 2018-03-03.
 * Copyright (c) 2018 skydoves rights reserved.
 */

@RunWith(AndroidJUnit4::class)
abstract class DBTest {
  lateinit var db: AppDatabase

  @Before
  fun initDB() {
    db = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),
        AppDatabase::class.java).build()
  }

  @After
  fun closeDB() {
    db.close()
  }
}

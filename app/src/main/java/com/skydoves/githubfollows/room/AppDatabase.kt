package com.skydoves.githubfollows.room

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

/**
 * Developed by skydoves on 2018-01-27.
 * Copyright (c) 2018 skydoves rights reserved.
 */

@Database(entities = arrayOf(History::class), version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun historyDAO(): HistoryDao
}

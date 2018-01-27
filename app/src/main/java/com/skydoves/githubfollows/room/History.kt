package com.skydoves.githubfollows.room

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Developed by skydoves on 2018-01-27.
 * Copyright (c) 2018 skydoves rights reserved.
 */

@Entity(tableName = "SearchHistory")
data class History(
    @PrimaryKey val search: String,
    val history: Long
)

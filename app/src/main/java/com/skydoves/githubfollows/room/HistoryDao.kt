package com.skydoves.githubfollows.room

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.skydoves.githubfollows.models.History

/**
 * Developed by skydoves on 2018-01-27.
 * Copyright (c) 2018 skydoves rights reserved.
 */

@Dao
interface HistoryDao {
    @Query("SELECT* FROM SearchHistory ORDER BY history DESC LIMIT 20")
    fun selectRecentHistoryList(): LiveData<List<History>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertHistory(history: History)

    @Query("DELETE FROM SearchHistory WHERE search = :search")
    fun deleteHistory(search: String)
}

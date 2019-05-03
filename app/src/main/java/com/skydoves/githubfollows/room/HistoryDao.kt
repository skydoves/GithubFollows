package com.skydoves.githubfollows.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
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

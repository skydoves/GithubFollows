package com.skydoves.githubfollows.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.skydoves.githubfollows.models.GithubUser

/**
 * Created by skydoves on 2018. 3. 6.
 * Copyright (c) 2018 skydoves All rights reserved.
 */

@Dao
interface GithubUserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGithubUser(githubUser: GithubUser)

    @Query("SELECT * FROM GithubUser WHERE login = :user COLLATE NOCASE LIMIT 1")
    fun getGithubUser(user: String): LiveData<GithubUser>

    @Query("DELETE FROM GithubUser")
    fun truncateGithubUser()
}

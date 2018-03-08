package com.skydoves.githubfollows.room

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.skydoves.githubfollows.models.GithubUser

/**
 * Created by skydoves on 2018. 3. 6.
 * Copyright (c) 2018 battleent All rights reserved.
 */

@Dao
interface GithubUserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGithubUser(githubUser: GithubUser)

    @Query("SELECT * FROM GithubUser WHERE login = :user LIMIT 1")
    fun getGithubUser(user: String): LiveData<GithubUser>

    @Query("DELETE FROM GithubUser")
    fun truncateGithubUser()
}

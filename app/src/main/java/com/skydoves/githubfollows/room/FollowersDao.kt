package com.skydoves.githubfollows.room

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.skydoves.githubfollows.models.Follower

/**
 * Created by skydoves on 2018. 3. 12.
 * Copyright (c) 2018 skydoves All rights reserved.
 */

@Dao
interface FollowersDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFollowers(followers: List<Follower>)

    @Query("SELECT * FROM Follower WHERE owner = :owner_ AND page = :page_ AND isFollower = :isFollower_")
    fun getFollowers(owner_: String, page_: Int, isFollower_: Boolean): LiveData<List<Follower>>
}

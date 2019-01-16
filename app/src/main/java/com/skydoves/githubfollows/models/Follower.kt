package com.skydoves.githubfollows.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import android.os.Parcel
import android.os.Parcelable

/**
 * Developed by skydoves on 2018-01-20.
 * Copyright (c) 2018 skydoves rights reserved.
 */

@Entity
data class Follower(@PrimaryKey(autoGenerate = true) val number: Int,
                    val login: String,
                    val id: Int,
                    val avatar_url: String,
                    val gravatar_id: String,
                    val url: String,
                    val html_url: String,
                    val followers_url: String,
                    val following_url: String,
                    val gists_url: String,
                    val starred_url: String,
                    val subscriptions_url: String,
                    val organizations_url: String,
                    val repos_url: String,
                    val events_url: String,
                    val received_events_url: String,
                    val type: String,
                    val site_admin: Boolean,
                    var owner: String,
                    var page: Int,
                    var isFollower: Boolean) : Parcelable {
    constructor(source: Parcel) : this(
            source.readInt(),
            source.readString(),
            source.readInt(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            1 == source.readInt(),
            source.readString(),
            source.readInt(),
            1 == source.readInt()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt(number)
        writeString(login)
        writeInt(id)
        writeString(avatar_url)
        writeString(gravatar_id)
        writeString(url)
        writeString(html_url)
        writeString(followers_url)
        writeString(following_url)
        writeString(gists_url)
        writeString(starred_url)
        writeString(subscriptions_url)
        writeString(organizations_url)
        writeString(repos_url)
        writeString(events_url)
        writeString(received_events_url)
        writeString(type)
        writeInt((if (site_admin) 1 else 0))
        writeString(owner)
        writeInt(page)
        writeInt((if (isFollower) 1 else 0))
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Follower> = object : Parcelable.Creator<Follower> {
            override fun createFromParcel(source: Parcel): Follower = Follower(source)
            override fun newArray(size: Int): Array<Follower?> = arrayOfNulls(size)
        }
    }
}
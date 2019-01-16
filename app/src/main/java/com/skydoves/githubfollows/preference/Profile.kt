package com.skydoves.githubfollows.preference

import com.skydoves.preferenceroom.KeyName
import com.skydoves.preferenceroom.PreferenceEntity

/**
 * Developed by skydoves on 2018-01-27.
 * Copyright (c) 2018 skydoves rights reserved.
 */

@Suppress("unused")
@PreferenceEntity(name = "UserProfile")
open class Profile {
    @KeyName(name = "name")
    @JvmField
    val userName = "skydoves"
    @KeyName(name = "menuPosition")
    @JvmField
    val selectedPosition = 0
}

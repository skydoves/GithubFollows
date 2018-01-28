package com.skydoves.githubfollows.preference;

import com.skydoves.preferenceroom.KeyName;
import com.skydoves.preferenceroom.PreferenceEntity;

/**
 * Developed by skydoves on 2018-01-27.
 * Copyright (c) 2018 skydoves rights reserved.
 */

@PreferenceEntity(name = "UserProfile")
public class Profile {
    @KeyName(name = "name") protected final String userName = "skydoves";
    @KeyName(name = "menuPosition") protected final int selectedPosition = 0;
}

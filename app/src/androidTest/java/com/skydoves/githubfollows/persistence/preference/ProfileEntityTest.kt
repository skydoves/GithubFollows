package com.skydoves.githubfollows.persistence.preference

import android.content.Context
import android.content.SharedPreferences
import android.support.test.runner.AndroidJUnit4
import com.skydoves.githubfollows.preference.PreferenceComponent_PrefAppComponent
import com.skydoves.githubfollows.preference.Preference_UserProfile
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.notNullValue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Developed by skydoves on 2018-03-03.
 * Copyright (c) 2018 skydoves rights reserved.
 */

@RunWith(AndroidJUnit4::class)
class ProfileEntityTest : PreferenceTest() {

    private lateinit var sharedPreferences: SharedPreferences

    private lateinit var component: PreferenceComponent_PrefAppComponent
    private lateinit var profile: Preference_UserProfile

    private val test_name = "test_skydoves"
    private val test_position = 1000

    @Before
    fun initialize() {
        component = PreferenceComponent_PrefAppComponent.getInstance()
        profile = Preference_UserProfile.getInstance(context)
        sharedPreferences = context.getSharedPreferences(profile.entityName, Context.MODE_PRIVATE)
    }

    @Test
    fun initTest() {
        assertThat(component, notNullValue())
        assertThat(profile, notNullValue())
    }

    @Test
    fun profileTest() {
        profile.clear()
        sharedPreferences.edit().apply {
            putString(profile.nameKeyName(), test_name)
            putInt(profile.menuPositionKeyName(), test_position)
        }.apply()

        assertThat(profile.name, `is`(test_name))
        assertThat(profile.menuPosition, `is`(test_position))
        assertThat(sharedPreferences.getString(profile.nameKeyName(), ""), `is`(profile.name))
        assertThat(sharedPreferences.getInt(profile.menuPositionKeyName(), 0), `is`(profile.menuPosition))
    }
}

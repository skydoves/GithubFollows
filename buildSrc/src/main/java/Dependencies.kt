/**
 * Developed by skydoves on 2018-02-17.
 * Copyright (c) 2018 skydoves rights reserved.
 */

object Versions {
    val compile_sdk = 27
    val min_sdk = 16
    val target_sdk = 27
    val version_code = 1
    val version_name = "1.0.0"

    val gradle = "3.0.1"
    val junit = "4.12"
    val support_library = "27.0.2"
    val kotlin = "1.2.21"
    val anko = "0.10.1"
    val arch_component = "1.0.0"
    val dagger ="2.11"
    val rxjava = "2.1.8"
    val rxandroid = "2.0.1"
    val preferenceroom = "1.0.9"
    val retrofit = "2.3.0"
    val gson = "2.8.2"
    val timber = "4.6.0"
    val glide = "4.5.0"
    val powermenu = "1.0.9"
    val androidsvg = "1.2.1"
    val shimmer = "1.1.0"
}

object Deps {
    val gradle = "com.android.tools.build:gradle:${Versions.gradle}"
    val junit = "junit:junit:${Versions.junit}"
    val support_appcompat = "com.android.support:appcompat-v7:${Versions.support_library}"
    val support_design = "com.android.support:design:${Versions.support_library}"
    val support_cardview = "com.android.support:cardview-v7:${Versions.support_library}"
    val support_recyclerview = "com.android.support:recyclerview-v7:${Versions.support_library}"
    val kotlin_plugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
    val kotlin_stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jre7:${Versions.kotlin}"
    val anko = "org.jetbrains.anko:anko:${Versions.anko}"
    val arch_lifecycle = "android.arch.lifecycle:extensions:${Versions.arch_component}"
    val arch_room = "android.arch.persistence.room:runtime:${Versions.arch_component}"
    val arch_room_compiler = "android.arch.persistence.room:compiler:${Versions.arch_component}"
    val dagger = "com.google.dagger:dagger:${Versions.dagger}"
    val dagger_compiler = "com.google.dagger:dagger-compiler:${Versions.dagger}"
    val dagger_android = "com.google.dagger:dagger-android:${Versions.dagger}"
    val dagger_android_support = "com.google.dagger:dagger-android-support:${Versions.dagger}"
    val dagger_android_compiler = "com.google.dagger:dagger-android-processor:${Versions.dagger}"
    val rxjava = "io.reactivex.rxjava2:rxjava:${Versions.rxjava}"
    val rxandroid = "io.reactivex.rxjava2:rxandroid:${Versions.rxandroid}"
    val preferenceroom = "com.github.skydoves:preferenceroom:${Versions.preferenceroom}"
    val preferenceroom_compiler = "com.github.skydoves:preferenceroom-processor:${Versions.preferenceroom}"
    val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    val retrofit_gson = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
    val gson = "com.google.code.gson:gson:${Versions.gson}"
    val timber = "com.jakewharton.timber:timber:${Versions.timber}"
    val glide = "com.github.bumptech.glide:glide:${Versions.glide}"
    val glide_compiler = "com.github.bumptech.glide:compiler:${Versions.glide}"
    val powermenu = "com.github.skydoves:powermenu:${Versions.powermenu}"
    val androidsvg = "com.caverock:androidsvg:${Versions.androidsvg}"
    val shimmerlayout = "io.supercharge:shimmerlayout:${Versions.shimmer}"
}
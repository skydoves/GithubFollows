
/**
 * Developed by skydoves on 2018-02-17.
 * Copyright (c) 2018 skydoves rights reserved.
 */

object Dependencies {
    val gradle = "com.android.tools.build:gradle:${Versions.gradle}"
    val junit = "junit:junit:${Versions.junit}"
    val support_appcompat = "com.android.support:appcompat-v7:${Versions.supportLibrary}"
    val support_design = "com.android.support:design:${Versions.supportLibrary}"
    val support_cardview = "com.android.support:cardview-v7:${Versions.supportLibrary}"
    val support_recyclerview = "com.android.support:recyclerview-v7:${Versions.supportLibrary}"
    val kotlin_plugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
    val kotlin_stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jre7:${Versions.kotlin}"
    val dataBinding = "com.android.databinding:compiler:${Versions.dataBinding}"
    val anko = "org.jetbrains.anko:anko:${Versions.anko}"
    val arch_lifecycle = "android.arch.lifecycle:extensions:${Versions.archComponent}"
    val arch_room = "android.arch.persistence.room:runtime:${Versions.archComponent}"
    val arch_room_compiler = "android.arch.persistence.room:compiler:${Versions.archComponent}"
    val dagger = "com.google.dagger:dagger:${Versions.dagger}"
    val dagger_compiler = "com.google.dagger:dagger-compiler:${Versions.dagger}"
    val dagger_android = "com.google.dagger:dagger-android:${Versions.dagger}"
    val dagger_android_support = "com.google.dagger:dagger-android-support:${Versions.dagger}"
    val dagger_android_compiler = "com.google.dagger:dagger-android-processor:${Versions.dagger}"
    val rxjava = "io.reactivex.rxjava2:rxjava:${Versions.rxJava}"
    val rxandroid = "io.reactivex.rxjava2:rxandroid:${Versions.rxAndroid}"
    val preferenceroom = "com.github.skydoves:preferenceroom:${Versions.preferenceRoom}"
    val preferenceroom_compiler = "com.github.skydoves:preferenceroom-processor:${Versions.preferenceRoom}"
    val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    val retrofit_gson = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
    val gson = "com.google.code.gson:gson:${Versions.gson}"
    val timber = "com.jakewharton.timber:timber:${Versions.timber}"
    val memoryLeakDebug = "com.squareup.leakcanary:leakcanary-android:${Versions.memoryLeak}"
    val memoryLeakRelease = "com.squareup.leakcanary:leakcanary-android-no-op:${Versions.memoryLeak}"
    val glide = "com.github.bumptech.glide:glide:${Versions.glide}"
    val glide_compiler = "com.github.bumptech.glide:compiler:${Versions.glide}"
    val powermenu = "com.github.skydoves:powermenu:${Versions.powerMenu}"
    val androidsvg = "com.caverock:androidsvg:${Versions.androidSvg}"
    val shimmerlayout = "io.supercharge:shimmerlayout:${Versions.shimmer}"
}
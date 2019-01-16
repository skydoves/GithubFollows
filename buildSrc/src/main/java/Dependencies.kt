@file:Suppress("SpellCheckingInspection")

/**
 * Developed by skydoves on 2018-02-17.
 * Copyright (c) 2018 skydoves rights reserved.
 */

@Suppress("unused", "MayBeConstant")
object Dependencies {
    val gradle = "com.android.tools.build:gradle:${Versions.gradle}"
    val benManes = "com.github.ben-manes:gradle-versions-plugin:${Versions.benManes}"
    val junit = "junit:junit:${Versions.junit}"
    val support_appcompat = "androidx.appcompat:appcompat:${Versions.accompat}"
    val support_design = "com.google.android.material:material:${Versions.material}"
    val support_cardview = "androidx.cardview:cardview:${Versions.androidx}"
    val support_recyclerview = "androidx.recyclerview:recyclerview:${Versions.androidx}"
    val kotlin_plugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
    val kotlin_stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"
    val anko = "org.jetbrains.anko:anko:${Versions.anko}"
    val arch_lifecycle = "android.arch.lifecycle:extensions:${Versions.archComponent}"
    val arch_room = "android.arch.persistence.room:runtime:${Versions.archComponent}"
    val arch_room_compiler = "android.arch.persistence.room:compiler:${Versions.archComponent}"
    val arch_core_testing = "android.arch.core:core-testing:${Versions.archCoreTesting}"
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
    val baseadapter = "com.github.skydoves:baserecyclerviewadapter:${Versions.baseAdapter}"
    val androidsvg = "com.caverock:androidsvg:${Versions.androidSvg}"
    val androidveil = "com.github.skydoves:androidveil:${Versions.androidVeil}"
    val mockito = "org.mockito:mockito-core:${Versions.mockito}"
    val mockitoAndroid = "org.mockito:mockito-android:${Versions.mockito}"
    val mockWebServer = "com.squareup.okhttp3:mockwebserver:${Versions.mockWebServer}"
    val espresso = "com.android.support.test.espresso:espresso-core:${Versions.espresso}"
    val stetho = "com.facebook.stetho:stetho:${Versions.stetho}"
    val stetho_okhttp = "com.facebook.stetho:stetho-okhttp3:${Versions.stetho}"
}
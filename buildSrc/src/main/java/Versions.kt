/**
 * Developed by skydoves on 2018-02-18.
 * Copyright (c) 2018 skydoves rights reserved.
 */

@Suppress("unused", "SpellCheckingInspection")
object Versions {
  private const val versionMajor = 1
  private const val versionMinor = 0
  private const val versionPatch = 0

  const val compileSdk = 29
  const val minSdk = 16
  const val targetSdk = 29
  const val versionCode = versionMajor * 100 + versionMinor * 10 + versionPatch
  const val versionName = "$versionMajor.$versionMinor.$versionPatch"

  const val gradle = "3.4.2"
  const val benManes = "0.20.0"
  const val spotless = "3.24.1"
  const val junit = "4.12"
  const val androidx = "1.0.0"
  const val accompat = "1.1.0-alpha05"
  const val livedataKtx = "2.1.0-beta01"
  const val material = "1.1.0-alpha01"
  const val kotlin = "1.3.41"
  const val anko = "0.10.8"
  const val archComponent = "2.0.0"
  const val archCoreTesting = "1.1.1"
  const val dagger = "2.24"
  const val rxJava = "2.2.11"
  const val rxAndroid = "2.1.1"
  const val preferenceRoom = "1.1.8"
  const val retrofit = "2.5.0"
  const val gson = "2.8.5"
  const val timber = "4.7.1"
  const val memoryLeak = "1.6.3"
  const val glide = "4.9.0"
  const val powerMenu = "2.1.1"
  const val androidSvg = "1.4"
  const val baseAdapter = "0.1.3"
  const val androidVeil = "1.0.6"
  const val mockito = "3.0.0"
  const val mockitoKotlin = "2.1.0"
  const val mockWebServer = "3.14.0"
  const val espresso = "3.0.2"
  const val stetho = "1.5.1"
  const val multiDex = "2.0.1"
}
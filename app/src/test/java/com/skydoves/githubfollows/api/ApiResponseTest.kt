/*
 * The MIT License (MIT)
 *
 * Designed and developed by 2018 skydoves (Jaewoong Eum)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.skydoves.githubfollows.api

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.CoreMatchers.nullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Response

/**
 * Created by skydoves on 2018. 3. 13.
 * Copyright (c) 2018 skydoves All rights reserved.
 */

@RunWith(JUnit4::class)
class ApiResponseTest {

  @Test
  fun exception() {
    val exception = Exception("foo")
    val apiResponse = ApiResponse<String>(exception)
    assertThat<Map<String, String>>(apiResponse.links, notNullValue())
    assertThat<String>(apiResponse.body, nullValue())
    assertThat(apiResponse.code, `is`(500))
    assertThat(apiResponse.envelope?.message, `is`("foo"))
  }

  @Test
  fun success() {
    val apiResponse = ApiResponse(Response.success("foo"))
    assertThat(apiResponse.envelope?.message, nullValue())
    assertThat(apiResponse.code, `is`(200))
    assertThat<String>(apiResponse.body, `is`("foo"))
    assertThat<Int>(apiResponse.nextPage, `is`(nullValue()))
  }

  @Test
  fun link() {
    val link = "<https://api.github.com/search/repositories?q=foo&page=2>; rel=\"next\"," + " <https://api.github.com/search/repositories?q=foo&page=34>; rel=\"last\""
    val headers = okhttp3.Headers.of("link", link)
    val response = ApiResponse(Response.success("foo", headers))
    assertThat<Int>(response.nextPage, `is`(2))
  }

  @Test
  fun badPageNumber() {
    val link = "<https://api.github.com/search/repositories?q=foo&page=dsa>; rel=\"next\""
    val headers = okhttp3.Headers.of("link", link)
    val response = ApiResponse(Response.success("foo", headers))
    assertThat<Int>(response.nextPage, nullValue())
  }

  @Test
  fun badLinkHeader() {
    val link = "<https://api.github.com/search/repositories?q=foo&page=dsa>; relx=\"next\""
    val headers = okhttp3.Headers.of("link", link)
    val response = ApiResponse(Response.success("foo", headers))
    assertThat<Int>(response.nextPage, nullValue())
  }
}

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

import androidx.collection.ArrayMap
import com.google.gson.Gson
import com.skydoves.githubfollows.models.Envelope
import retrofit2.Response
import timber.log.Timber
import java.io.IOException
import java.util.regex.Pattern

/**
 * Developed by skydoves on 2018-02-20.
 * Copyright (c) 2018 skydoves rights reserved.
 */

@Suppress("JoinDeclarationAndAssignment")
class ApiResponse<T> {
  val code: Int
  val body: T?
  var links: MutableMap<String, String>
  private val gson: Gson
  val envelope: Envelope?

  val isSuccessful: Boolean
    get() = code in 200..300

  val nextPage: Int?
    get() {
      val next = links[NEXT_LINK] ?: return null
      val matcher = PAGE_PATTERN.matcher(next)
      if (!matcher.find() || matcher.groupCount() != 1) {
        return null
      }
      try {
        return Integer.parseInt(matcher.group(1))
      } catch (ex: NumberFormatException) {
        Timber.w("cannot parse next page from %s", next)
        return null
      }
    }

  init {
    gson = Gson()
    links = emptyMap<String, String>().toMutableMap()
  }

  constructor(error: Throwable) {
    code = 500
    body = null
    envelope = Envelope(error.message.toString(), "")
  }

  constructor(response: Response<T>) {
    code = response.code()
    if (response.isSuccessful) {
      body = response.body()
      envelope = null
    } else {
      var message: String? = null
      response.errorBody()?.let {
        try {
          message = response.errorBody()!!.string()
        } catch (ignored: IOException) {
          Timber.e(ignored, "error while parsing response")
        }
      }

      message?.apply {
        if (isNullOrEmpty() || trim { it <= ' ' }.isEmpty()) {
          message = response.message()
        }
      }

      envelope = gson.fromJson(message, Envelope::class.java)
      body = null
    }

    val linkHeader = response.headers().get("link")
    linkHeader?.let {
      links = ArrayMap()
      val matcher = LINK_PATTERN.matcher(linkHeader)
      while (matcher.find()) {
        if (matcher.groupCount() == 2) {
          links.put(matcher.group(2), matcher.group(1))
        }
      }
    }
  }

  companion object {
    private val LINK_PATTERN = Pattern.compile("<([^>]*)>[\\s]*;[\\s]*rel=\"([a-zA-Z0-9]+)\"")
    private val PAGE_PATTERN = Pattern.compile("\\bpage=(\\d+)")
    private val NEXT_LINK = "next"
  }
}

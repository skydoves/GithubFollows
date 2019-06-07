package com.skydoves.githubfollows.models

/**
 * Developed by skydoves on 2018-03-12.
 * Copyright (c) 2018 skydoves rights reserved.
 */

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
</T> */
@Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
class Resource<out T>(
  val status: Status,
  val data: T?,
  val message: String?,
  val fetchStatus: FetchStatus
) {

  override fun equals(obj: Any?): Boolean {
    if (this === obj) {
      return true
    }
    if (obj == null || javaClass != obj.javaClass) {
      return false
    }

    val resource = obj as Resource<*>?

    if (status !== resource!!.status) {
      return false
    }
    if (if (message != null) message != resource!!.message else resource!!.message != null) {
      return false
    }
    return if (data != null) data == resource.data else resource.data == null
  }

  override fun toString(): String {
    return "Resource{" +
      "status=" + status +
      ", message='" + message + '\'' +
      ", data=" + data +
      '}'
  }

  override fun hashCode(): Int {
    var result = 17
    result = 31 * result + status.hashCode()
    result = 31 * result + data.hashCode()
    result = 31 * result + message.hashCode()
    result = 31 * result + fetchStatus.hashCode()
    return result
  }

  companion object {
    private var fetchStatus = FetchStatus()

    fun <T> success(data: T?, nextPage: Int?): Resource<T> {
      fetchStatus = FetchStatus(false, true, false, false, nextPage)
      if (nextPage == null) fetchStatus = FetchStatus(false, true, false, true, nextPage)
      return Resource(Status.SUCCESS, data, null, fetchStatus)
    }

    fun <T> error(msg: String, data: T?): Resource<T> {
      fetchStatus = FetchStatus(false, false, true, true)
      return Resource(Status.ERROR, data, msg, fetchStatus)
    }

    fun <T> loading(data: T?, nextPage: Int?): Resource<T> {
      fetchStatus = FetchStatus(true, false, false, false, nextPage)
      return Resource(Status.LOADING, data, null, fetchStatus)
    }
  }
}

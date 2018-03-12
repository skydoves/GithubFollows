
package com.skydoves.githubfollows.models

/**
 * Developed by skydoves on 2018-03-12.
 * Copyright (c) 2018 skydoves rights reserved.
 */

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
</T> */
class Resource<T>(val status: Status, val data: T?, val message: String?, val nextPage: Int?) {

    override fun equals(o: Any?): Boolean {
        if (this === o) {
            return true
        }
        if (o == null || javaClass != o.javaClass) {
            return false
        }

        val resource = o as Resource<*>?

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

    companion object {

        fun <T> success(data: T?, nextPage: Int?): Resource<T> {
            return Resource(Status.SUCCESS, data, null, nextPage)
        }

        fun <T> error(msg: String, data: T?, nextPage: Int?): Resource<T> {
            return Resource(Status.ERROR, data, msg, nextPage)
        }

        fun <T> loading(data: T?, nextPage: Int?): Resource<T> {
            return Resource(Status.LOADING, data, null, nextPage)
        }
    }

    fun isOnLoading(): Boolean {
        if(status == Status.LOADING) return true
        return false
    }

    fun isOnLast(): Boolean {
        if(status == Status.SUCCESS && nextPage == null) return true
        return false
    }

    fun isOnError(): Boolean {
        if(status == Status.ERROR) return true
        return false
    }
}

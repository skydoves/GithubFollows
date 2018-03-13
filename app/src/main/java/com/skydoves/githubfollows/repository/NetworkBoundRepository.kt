package com.skydoves.githubfollows.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.support.annotation.MainThread
import android.support.annotation.WorkerThread
import com.skydoves.githubfollows.api.ApiResponse
import com.skydoves.githubfollows.models.Envelope
import com.skydoves.githubfollows.models.Resource
import timber.log.Timber

/**
 * Created by skydoves on 2018. 3. 6.
 * Copyright (c) 2018 skydoves All rights reserved.
 */

abstract class NetworkBoundRepository<ResultType, RequestType>
internal constructor() {

    private val result: MediatorLiveData<Resource<ResultType>> = MediatorLiveData()

    init {
        Timber.d("Injection NetworkBoundRepository")
        result.postValue(Resource.loading(null, null))
        val loadedFromDB = loadFromDb()
        result.addSource(loadedFromDB) { data ->
            result.removeSource(loadedFromDB)
            if (shouldFetch(data)) {
                fetchFromNetwork(loadedFromDB)
            } else {
                result.addSource<ResultType>(loadedFromDB) { newData -> setValue(Resource.success(newData, 1)) }
            }
        }
    }

    private fun fetchFromNetwork(loadedFromDB: LiveData<ResultType>) {
        val apiResponse = fetchService()
        result.addSource(loadedFromDB) { newData -> setValue(Resource.loading(newData, 1))}
        result.addSource(apiResponse) { response ->
            result.removeSource(apiResponse)
            result.removeSource(loadedFromDB)
            response?.let {
                when(response.isSuccessful) {
                    true -> {
                        response.body?.let {
                            saveFetchData(it)
                            val loaded = loadFromDb()
                            result.addSource(loaded) { newData ->
                                result.removeSource(loaded)
                                setValue(Resource.success(newData, response.nextPage))
                            }
                        }
                    }
                    false -> {
                        onFetchFailed(response.envelope)
                        response.envelope?.let {
                            result.addSource<ResultType>(loadedFromDB) {
                                newData -> setValue(Resource.error(it.message, newData, null)) }
                        }
                    }
                }
            }
        }
    }

    @MainThread
    private fun setValue(newValue: Resource<ResultType>) {
        result.value = newValue
    }

    fun asLiveData(): LiveData<Resource<ResultType>> {
        return result
    }

    @WorkerThread
    protected abstract fun saveFetchData(item: RequestType)

    @MainThread
    protected abstract fun shouldFetch(data: ResultType?): Boolean

    @MainThread
    protected abstract fun loadFromDb(): LiveData<ResultType>

    @MainThread
    protected abstract fun fetchService(): LiveData<ApiResponse<RequestType>>

    @MainThread
    protected abstract fun onFetchFailed(envelope: Envelope?)
}

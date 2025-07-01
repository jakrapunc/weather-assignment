package com.kabigon.weatherforecast.data.base

import com.kabigon.weatherforecast.data.base.model.ErrorResponse
import com.kabigon.weatherforecast.data.base.model.ResultResponse
import retrofit2.Response

abstract class DataBoundResource<ResponseType> {

    protected var result: ResultResponse<ResponseType> = ResultResponse.Error(
        ErrorResponse()
    )

    suspend fun asResult(): ResultResponse<ResponseType> {
        val dbSource = loadFromDb()
        val shouldFetch = shouldFetch(dbSource)
        if (shouldFetch) {
            fetchFromNetwork(dbSource)
        } else {
            return ResultResponse.Success(dbSource)
        }
        return result
    }

    protected abstract suspend fun fetchFromNetwork(dbSource: ResponseType?)

    protected abstract suspend fun saveCallResult(item: ResponseType)

    protected abstract suspend fun shouldFetch(data: ResponseType?): Boolean

    protected abstract suspend fun loadFromDb(): ResponseType?

    protected abstract suspend fun createCall(): Response<ResponseType>
}
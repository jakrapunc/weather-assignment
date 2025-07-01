package com.kabigon.weatherforecast.data.base

import com.google.gson.Gson
import com.kabigon.weatherforecast.data.base.extension.toNetworkResult
import com.kabigon.weatherforecast.data.base.model.ApiError
import com.kabigon.weatherforecast.data.base.model.ErrorResponse
import com.kabigon.weatherforecast.data.base.model.ResultResponse

abstract class DirectNetworkBoundResource<ResponseType>: DataBoundResource<ResponseType>() {

    override suspend fun fetchFromNetwork(dbSource: ResponseType?) {
        val response: ResultResponse<ResponseType> = try {
            createCall().toNetworkResult()
        } catch (e: Exception) {
            val errorMessage = e.message ?: "unknown error"
            ResultResponse.Error(
                ErrorResponse(
                    message = errorMessage,
                    exception = e
                )
            )
        }

        result = when (response) {
            is ResultResponse.Success -> {
                response.data?.let {
                    saveCallResult(it)
                }
                ResultResponse.Success(response.data)
            }

            is ResultResponse.Error -> {
                response
            }
        }
    }

    override suspend fun saveCallResult(item: ResponseType) = Unit

    override suspend fun shouldFetch(data: ResponseType?): Boolean = true

    override suspend fun loadFromDb(): ResponseType? {
        return null
    }
}
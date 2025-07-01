package com.kabigon.weatherforecast.data.base.model

sealed class ResultResponse<out R> {
     data class Success<out T>(val data: T?) : ResultResponse<T>()
     data class Error(val exception: ErrorResponse) : ResultResponse<Nothing>()
}
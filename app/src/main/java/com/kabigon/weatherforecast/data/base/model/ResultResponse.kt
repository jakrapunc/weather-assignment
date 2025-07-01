package com.kabigon.weatherforecast.data.base.model

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

sealed class ResultResponse<out R> {
     data class Success<out T>(val data: T?) : ResultResponse<T>()
     data class Error(val exception: ErrorResponse) : ResultResponse<Nothing>()
}

fun <T> Flow<ResultResponse<T>>.onSuccess(action: suspend (data: T?) -> Unit) =
     onEach {
          if (it is ResultResponse.Success) {
               action(it.data)
          }
     }

fun <T> Flow<ResultResponse<T>>.onError(action: suspend (errorResponse: ErrorResponse?) -> Unit) =
     onEach {
          if (it is ResultResponse.Error) {
               action(it.exception)
          }
     }

fun <T> Flow<ResultResponse<T>>.asResult() = map {
     when (it) {
          is ResultResponse.Success -> it.data
          is ResultResponse.Error -> null
     }
}
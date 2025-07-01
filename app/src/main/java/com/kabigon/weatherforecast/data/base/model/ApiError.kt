package com.kabigon.weatherforecast.data.base.model

import com.google.gson.annotations.SerializedName

data class ApiError(
    @SerializedName("cod")
    val code: String,
    @SerializedName("message")
    val message: String
)

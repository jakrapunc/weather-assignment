package com.kabigon.weatherforecast.data.model

import com.google.gson.annotations.SerializedName

data class Climate(
    @SerializedName("1h")
    val oneHour: Double
)

package com.kabigon.weatherforecast.data.model.response

import com.google.gson.annotations.SerializedName
import com.kabigon.weatherforecast.data.model.Clouds
import com.kabigon.weatherforecast.data.model.Main
import com.kabigon.weatherforecast.data.model.Climate
import com.kabigon.weatherforecast.data.model.Sys
import com.kabigon.weatherforecast.data.model.Weather
import com.kabigon.weatherforecast.data.model.Wind

data class WeatherResponse(
    @SerializedName("weather")
    val weather: List<Weather>,
    @SerializedName("main")
    val main: Main,
    @SerializedName("wind")
    val wind: Wind,
    @SerializedName("dt")
    val timeStamp: Long,
    @SerializedName("name")
    val cityName: String,
    @SerializedName("cod")
    val cod: Int,
    @SerializedName("timezone")
    val timezone: Int,
    @SerializedName("sys")
    val sys: Sys,
    @SerializedName("id")
    val id: Int,
    @SerializedName("clouds")
    val clouds: Clouds,
    @SerializedName("rain")
    val rain: Climate?,
    @SerializedName("snow")
    val snow: Climate?
)
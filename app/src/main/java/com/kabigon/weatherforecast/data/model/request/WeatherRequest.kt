package com.kabigon.weatherforecast.data.model.request

data class WeatherRequest(
    val cityName: String,
    val apiKey: String,
    val units: String = "metric"
)
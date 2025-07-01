package com.kabigon.weatherforecast.data.service.repository.remote

import com.kabigon.weatherforecast.data.base.network.ApiManager
import com.kabigon.weatherforecast.data.model.response.WeatherResponse
import com.kabigon.weatherforecast.data.service.api.WeatherService
import retrofit2.Response

interface IWeatherRemote {
    suspend fun getWeather(
        cityName: String,
        apiKey: String,
        units: String
    ):Response<WeatherResponse>
}

class WeatherRemote(
    private val apiManager: ApiManager
): IWeatherRemote {

    companion object {
        private const val API_VERSION = "2.5"
    }

    private val weatherService = apiManager.init(
        "http://api.openweathermap.org/"
    ).create(WeatherService::class.java)

    override suspend fun getWeather(
        cityName: String,
        apiKey: String,
        units: String
    ): Response<WeatherResponse> {
        return weatherService.getWeather(
            API_VERSION,
            cityName,
            apiKey,
            units
        )
    }
}
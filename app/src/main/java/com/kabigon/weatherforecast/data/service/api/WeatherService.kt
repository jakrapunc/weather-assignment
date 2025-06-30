package com.kabigon.weatherforecast.data.service.api

import com.kabigon.weatherforecast.data.model.response.WeatherResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WeatherService {

    @GET("/data/{version}/weather")
    suspend fun getWeather(
        @Path("version") version: String,
        @Query("q") cityName: String,
        @Query("appid") apiKey: String,
        @Query("units") units: String
    ): Response<WeatherResponse>
}
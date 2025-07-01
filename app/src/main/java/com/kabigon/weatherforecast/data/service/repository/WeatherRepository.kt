package com.kabigon.weatherforecast.data.service.repository

import com.kabigon.weatherforecast.data.base.DirectNetworkBoundResource
import com.kabigon.weatherforecast.data.base.model.ResultResponse
import com.kabigon.weatherforecast.data.model.request.WeatherRequest
import com.kabigon.weatherforecast.data.model.response.WeatherResponse
import com.kabigon.weatherforecast.data.service.repository.remote.IWeatherRemote
import retrofit2.Response

interface IWeatherRepository {
    suspend fun getWeather(
        request: WeatherRequest
    ): ResultResponse<WeatherResponse>
}

class WeatherRepository(
    private val weatherRemote: IWeatherRemote,
): IWeatherRepository {

    override suspend fun getWeather(
        request: WeatherRequest
    ): ResultResponse<WeatherResponse> {
        return object : DirectNetworkBoundResource<WeatherResponse>() {
            override suspend fun createCall(): Response<WeatherResponse> {
                return weatherRemote.getWeather(
                    cityName = request.cityName,
                    apiKey = request.apiKey,
                    units = request.units
                )
            }

            override suspend fun saveCallResult(item: WeatherResponse) {
                //save result to local db
            }

            override suspend fun shouldFetch(data: WeatherResponse?): Boolean {
                //todo check
                return true
            }

//            override suspend fun loadFromDb(): WeatherResponse? {
//                //todo load from db
//                return null
//            }
        }.asResult()
    }
}
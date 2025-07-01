package com.kabigon.weatherforecast.di

import com.kabigon.weatherforecast.data.base.network.ApiManager
import com.kabigon.weatherforecast.data.base.network.NetworkClient
import com.kabigon.weatherforecast.data.service.repository.IWeatherRepository
import com.kabigon.weatherforecast.data.service.repository.WeatherRepository
import com.kabigon.weatherforecast.data.service.repository.remote.IWeatherRemote
import com.kabigon.weatherforecast.data.service.repository.remote.WeatherRemote
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

val networkModule = module {
    single<CoroutineDispatcher>(named("io")) { Dispatchers.IO }

    factory {
        ApiManager(
            get(),
            Retrofit.Builder()
        )
    }

    factory {
        NetworkClient(
            androidContext()
        )
    }

    factory<IWeatherRemote> {
        WeatherRemote(
            get()
        )
    }

    factory<IWeatherRepository> {
        WeatherRepository(
            get()
        )
    }
}
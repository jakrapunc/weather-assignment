package com.kabigon.weatherforecast

import android.app.Application
import com.kabigon.weatherforecast.di.forecastModule
import com.kabigon.weatherforecast.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class WeatherApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            // Log Koin into Android logger
            androidLogger()
            // Reference Android context
            androidContext(this@WeatherApplication)
            // Load modules
            modules(appModule)
        }
    }
}

val appModule = listOf(
    networkModule,
    forecastModule
)
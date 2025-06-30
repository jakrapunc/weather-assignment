package com.kabigon.weatherforecast.di

import com.kabigon.weatherforecast.data.base.ApiManager
import com.kabigon.weatherforecast.data.base.NetworkClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit

val networkModule = module {
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
}
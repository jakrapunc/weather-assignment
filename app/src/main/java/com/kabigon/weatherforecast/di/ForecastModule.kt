package com.kabigon.weatherforecast.di

import com.kabigon.weatherforecast.ui.forecast.ForecastViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val forecastModule = module {
    viewModel {
        ForecastViewModel(
            get(),
            get(named("io"))
        )
    }
}
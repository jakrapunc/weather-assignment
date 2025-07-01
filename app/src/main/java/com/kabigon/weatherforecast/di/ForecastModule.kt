package com.kabigon.weatherforecast.di

import com.kabigon.weatherforecast.ui.forecast.ForecastViewModel
import com.kabigon.weatherforecast.usecase.ForecastIconMapper
import com.kabigon.weatherforecast.usecase.TimezoneMapper
import org.koin.core.module.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val forecastModule = module {
    viewModel {
        ForecastViewModel(
            get(),
            get(),
            get(),
            get(named("io"))
        )
    }

    factory {
        TimezoneMapper()
    }

    factory {
        ForecastIconMapper()
    }
}
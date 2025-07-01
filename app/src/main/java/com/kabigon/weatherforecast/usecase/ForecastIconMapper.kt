package com.kabigon.weatherforecast.usecase

import com.kabigon.weatherforecast.R

class ForecastIconMapper {

    operator fun invoke(iconName: String): Int {
        when (iconName) {
            "01d" -> return R.drawable.ic01d
            "01n" -> return R.drawable.ic01n
            "02d" -> return R.drawable.ic02d
            "02n" -> return R.drawable.ic02n
            "03d" -> return R.drawable.ic03d
            "03n" -> return R.drawable.ic03n
            "04d" -> return R.drawable.ic04d
            "04n" -> return R.drawable.ic04n
            "09d" -> return R.drawable.ic09d
            "09n" -> return R.drawable.ic09n
            "10d" -> return R.drawable.ic10d
            "10n" -> return R.drawable.ic10n
            "11d" -> return R.drawable.ic11d
            "11n" -> return R.drawable.ic11n
            "13d" -> return R.drawable.ic13d
            "13n" -> return R.drawable.ic13n
            "50d" -> return R.drawable.ic50d
            "50n" -> return R.drawable.ic50n
            else -> return R.drawable.ic01d
        }
    }
}
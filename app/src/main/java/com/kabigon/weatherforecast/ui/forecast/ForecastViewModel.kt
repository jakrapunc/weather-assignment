package com.kabigon.weatherforecast.ui.forecast

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kabigon.weatherforecast.data.base.model.asResult
import com.kabigon.weatherforecast.data.base.model.onError
import com.kabigon.weatherforecast.data.base.model.onSuccess
import com.kabigon.weatherforecast.data.model.request.WeatherRequest
import com.kabigon.weatherforecast.data.model.response.WeatherResponse
import com.kabigon.weatherforecast.data.service.repository.IWeatherRepository
import com.kabigon.weatherforecast.usecase.ForecastIconMapper
import com.kabigon.weatherforecast.usecase.TimezoneMapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.stateIn

class ForecastViewModel(
    private val repository: IWeatherRepository,
    private val iconMapper: ForecastIconMapper,
    private val timezoneMapper: TimezoneMapper,
    private val ioDispatcher: CoroutineDispatcher
): ViewModel() {

    private val _retry = MutableStateFlow(0)
    private val _isLoading = MutableStateFlow(false)
    private val _error = MutableStateFlow<String?>(null)
    private var _query = MutableStateFlow("")
    private val _response = MutableStateFlow<WeatherResponse?>(null)

    val response = combine(
        _retry,
        _query
    ) { _, query ->
        _isLoading.value = true
        _error.value = null
        _response.value = null

        repository.getWeather(
            request = WeatherRequest(
                cityName = query.lowercase(),
                apiKey = "ed1cdaa732fc2e16136da32c7391e799"
            )
        )
    }.onError {
        _error.value = it?.message
        _isLoading.value = false
    }.onSuccess {
        _error.value = null
        _isLoading.value = false
        _response.value = it
    }.asResult().flowOn(ioDispatcher).launchIn(viewModelScope)

    val uiState = combine(
        _isLoading,
        _error,
        _response
    ) { isLoading, error, response ->
        val times = response?.let { timezoneMapper(it.timeStamp, it.timezone.toLong()) }
        UIState(
            isLoading = isLoading,
            error = error,
            response = response,
            weatherIcon = response?.let { iconMapper(it.weather.first().icon) },
            date = times?.getOrNull(0),
            time = times?.getOrNull(1)
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = UIState()
    )

    fun onUIEvent(event: OnUIEvent) {
        when(event) {
            is OnUIEvent.Search -> {
                _query.value = event.query
            }
            is OnUIEvent.Retry -> {
                _retry.value += 1
            }
        }
    }

    sealed interface OnUIEvent {
        data class Search(val query: String) : OnUIEvent
        data object Retry : OnUIEvent
    }

    data class UIState(
        val isLoading: Boolean = false,
        val response: WeatherResponse? = null,
        val error: String? = null,
        val weatherIcon: Int? = null,
        val date: String? = null,
        val time: String? = null
    )
}
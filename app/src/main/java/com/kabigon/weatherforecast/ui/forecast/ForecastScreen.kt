package com.kabigon.weatherforecast.ui.forecast

import android.annotation.SuppressLint
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.kabigon.weatherforecast.R
import com.kabigon.weatherforecast.data.model.Clouds
import com.kabigon.weatherforecast.data.model.Main
import com.kabigon.weatherforecast.data.model.Climate
import com.kabigon.weatherforecast.data.model.Sys
import com.kabigon.weatherforecast.data.model.Weather
import com.kabigon.weatherforecast.data.model.Wind
import com.kabigon.weatherforecast.data.model.response.WeatherResponse
import com.kabigon.weatherforecast.ui.component.CustomTextField
import com.kabigon.weatherforecast.ui.theme.Typography
import kotlinx.coroutines.delay
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ForecastScreen(
    viewModel: ForecastViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ForecastScreen(
        uiState = uiState,
        onValueChange = {
            viewModel.onUIEvent(
                ForecastViewModel.OnUIEvent.Search(it)
            )
        }
    )
}

@SuppressLint("UseOfNonLambdaOffsetOverload")
@Composable
fun ForecastScreen(
    uiState: ForecastViewModel.UIState = ForecastViewModel.UIState(),
    onValueChange: (String) -> Unit = {},
    onRetry: () -> Unit = {}
) {
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        var textQuery by remember { mutableStateOf("") }
        var hasFocus by remember { mutableStateOf(false) }
        val searchDebounce = 3000L

        // This LaunchedEffect will re-launch whenever textValue changes.
        // If textValue changes again before the delay completes, the previous
        // coroutine launched by this effect will be cancelled and a new one started.
        LaunchedEffect(textQuery) {
            if (textQuery.isNotBlank()) {
                delay(searchDebounce)

                onValueChange(textQuery)
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 20.dp, vertical = 20.dp),
        ) {
            BoxWithConstraints(
                modifier = Modifier.fillMaxSize()
                    .zIndex(3f)
            ) {
                val screenHeight = this.maxHeight
                val targetOffsetY by animateDpAsState(
                    targetValue = if (hasFocus) 0.dp else screenHeight / 2,
                    label = "offsetYAnimationWithConstraints"
                )

                CustomTextField(
                    value = textQuery,
                    onValueChange = {
                        textQuery = it
                    },
                    onClear = { textQuery = "" },
                    modifier = Modifier
                        .offset(y = targetOffsetY)
                        .onFocusChanged { focusState: FocusState ->
                            if (focusState.isFocused) {
                                hasFocus = true
                            }
                        }
                )
            }

            if (uiState.isLoading) {
                LoadingScreen()
            } else {
                ResultScreen(
                    modifier = Modifier.fillMaxSize()
                        .padding(top = 80.dp)
                        .zIndex(0f),
                    uiState = uiState
                )
            }
        }
    }
}

@Composable
fun ResultScreen(
    modifier: Modifier = Modifier,
    uiState: ForecastViewModel.UIState
) {
    uiState.response?.let { weatherResponse ->
        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row {
                Text(
                    modifier = Modifier.alignByBaseline(),
                    text = "${weatherResponse.main.temp.toInt()}",
                    style = Typography.titleLarge.copy(
                        fontSize = 64.sp
                    )
                )
                Spacer(modifier = Modifier.size(4.dp))
                Text(
                    modifier = Modifier.alignByBaseline(),
                    text = "°C",
                    style = Typography.titleLarge.copy(fontSize = 32.sp)
                )
            }
            Row {
                Text(
                    modifier = Modifier.alignByBaseline(),
                    text = "Feels like ${weatherResponse.main.feelsLike.toInt()}",
                    style = Typography.titleLarge
                )
                Spacer(modifier = Modifier.size(4.dp))
                Text(
                    modifier = Modifier.alignByBaseline(),
                    text = "°C",
                    style = Typography.titleLarge.copy(fontSize = 12.sp)
                )
            }
            Spacer(modifier = Modifier.size(16.dp))

            Text(
                text = weatherResponse.cityName,
                style = Typography.titleLarge
            )

            Spacer(modifier = Modifier.size(8.dp))
            Text(
                text = uiState.time ?: "",
                style = Typography.titleLarge.copy(
                    fontSize = 32.sp
                )
            )
            Text(text = uiState.date ?: "")

            Spacer(modifier = Modifier.size(16.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                uiState.weatherIcon?.let {
                    Icon(
                        painter = painterResource(id = uiState.weatherIcon),
                        tint = Color.Unspecified,
                        contentDescription = "",
                    )
                    Text(
                        text = uiState.response.weather.getOrNull(0)?.description ?: "",
                        style = Typography.titleLarge
                    )
                }
            }

            Spacer(modifier = Modifier.size(32.dp))

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    DisplayItem(header = "Max", value = "${uiState.response.main.tempMax}", tail = "°C")
                    DisplayItem(header = "Min", value = "${uiState.response.main.tempMin}", tail = "°C")
                    DisplayItem(header = "Humidity", value = "${uiState.response.main.humidity}", tail = "%")
                    DisplayItem(header = "Pressure", value = "${uiState.response.main.pressure}", tail = "hPa")
                    DisplayItem(header = "Wind", value = "${uiState.response.wind.speed}", tail = "m/s")
                }
                Spacer(modifier = Modifier.size(48.dp))
                Column(

                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    DisplayItem(header = "Sunrise", value = "${uiState.sunrise}")
                    DisplayItem(header = "Sunset", value = "${uiState.sunset}")
                    DisplayItem(header = "Clouds", value = "${uiState.response.clouds.all}", tail = "%")
                    DisplayItem(header = "Rain", value = "${uiState.response.rain?.oneHour ?: "-"}", tail = "mm/h")
                    DisplayItem(header = "Snow", value = "${uiState.response.snow?.oneHour ?: "-"}", tail = "mm/h")
                }
            }
        }
    }
}

@Composable
fun DisplayItem(
    header: String,
    value: String,
    tail: String? = null
) {
    Row {
        Text(
            text = "$header:",
            style = Typography.bodyMedium,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.size(4.dp))
        Text(
            text = value,
            style = Typography.bodyMedium
        )
        if (tail != null && value != "-") {
            Text(
                text = tail,
                style = Typography.bodyMedium
            )
        }
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loading))

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        LottieAnimation(
            modifier = Modifier.size(84.dp),
            composition = composition,
            iterations = LottieConstants.IterateForever,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ResultScreenPreview() {
    ResultScreen(
        modifier = Modifier.fillMaxSize(),
        uiState = ForecastViewModel.UIState(
            weatherIcon = R.drawable.ic01d,
            date = "2 July 2025",
            time = "12:00",
            sunrise = "06:00",
            sunset = "18:00",
            response = WeatherResponse(
                cityName = "London",
                weather = listOf(
                    Weather(
                        id = 501,
                        description = "moderate rain",
                        icon = "10d",
                        main = "Rain"
                    )
                ),
                wind = Wind(
                    speed = 4.09,
                    deg = 121,
                    gust = 3.47
                ),
                main = Main(
                    temp = 300.15,
                    feelsLike = 300.15,
                    tempMin = 300.15,
                    tempMax = 300.15,
                    pressure = 1021,
                    humidity = 60,
                    seaLevel = 1021,
                    groundLevel = 910
                ),
                timeStamp = 1726660758,
                cod = 200,
                id = 3165523,
                timezone = 7200,
                sys = Sys(
                    country = "GB",
                    sunrise = 1726660758,
                    sunset = 1726660758
                ),
                clouds = Clouds(
                    all = 100
                ),
                rain = Climate(
                    2.73
                ),
                snow = Climate(
                    2.73
                )
            )
        )
    )
}

@Preview
@Composable
fun ForecastScreenPreview() {
    ForecastScreen(
        uiState = ForecastViewModel.UIState()
    )
}
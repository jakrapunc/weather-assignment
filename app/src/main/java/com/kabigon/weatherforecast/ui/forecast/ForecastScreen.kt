package com.kabigon.weatherforecast.ui.forecast

import android.annotation.SuppressLint
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kabigon.weatherforecast.ui.component.CustomTextField
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
        var textQuery by remember { mutableStateOf("London") }
        var hasFocus by remember { mutableStateOf(false) }
        val searchDebounce = 3000L

        // This LaunchedEffect will re-launch whenever textValue changes.
        // If textValue changes again before the delay completes, the previous
        // coroutine launched by this effect will be cancelled and a new one started.
        LaunchedEffect(textQuery) {
            if (textQuery.isNotBlank()) {
                delay(searchDebounce)

                onValueChange(textQuery)
            } else {
                onValueChange("")
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

            Column(
                modifier = Modifier.fillMaxSize()
                    .padding(top = 80.dp)
                    .background(Color.Red)
                    .zIndex(0f)
            ) {

            }
        }
    }
}

@Preview
@Composable
fun ForecastScreenPreview() {
    ForecastScreen(
        uiState = ForecastViewModel.UIState()
    )
}
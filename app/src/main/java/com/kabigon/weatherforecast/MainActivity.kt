package com.kabigon.weatherforecast

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kabigon.weatherforecast.ui.component.CustomTextField
import com.kabigon.weatherforecast.ui.theme.WeatherForecastTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WeatherForecastTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        modifier = Modifier.padding(innerPadding)
                    ) {

                    }
                }
            }
        }
    }
}

@Composable
fun InputLayout() {
    Column(
        modifier = Modifier.fillMaxWidth()
            .padding(horizontal = 20.dp)
    ) {

        CustomTextField(
            value = "London",
            onValueChange = {},
            placeholder = "Type Here"
        )
    }
}

@Preview(showBackground = true)
@Composable
fun InputLayoutPreview() {
    WeatherForecastTheme {
        InputLayout()
    }
}
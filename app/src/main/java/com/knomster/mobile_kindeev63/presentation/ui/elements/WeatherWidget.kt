package com.knomster.mobile_kindeev63.presentation.ui.elements

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.knomster.mobile_kindeev63.R
import com.knomster.mobile_kindeev63.domain.entities.WeatherWidgetData
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun WeatherWidget(weatherData: WeatherWidgetData) {
    val height = LocalConfiguration.current.screenWidthDp / 5 * 2 + 20
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(height.dp)
            .padding(10.dp),
    ) {
        Card(
            modifier = Modifier
                .fillMaxSize(),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            when (weatherData) {
                is WeatherWidgetData.WeatherData -> {
                    WidgetContent(weather = weatherData)
                }

                WeatherWidgetData.InternetError -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource(id = R.string.no_connection),
                            color = Color.Black,
                            textAlign = TextAlign.Center,
                            fontSize = (LocalConfiguration.current.screenWidthDp / 15).sp
                        )
                    }
                }

                WeatherWidgetData.LocationError -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource(id = R.string.no_location_permission),
                            color = Color.Black,
                            textAlign = TextAlign.Center,
                            fontSize = (LocalConfiguration.current.screenWidthDp / 15).sp
                        )
                    }
                }

                WeatherWidgetData.Load -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.testTag("loadBar"),
                            color = Color.Black
                        )
                    }

                }
            }
        }
    }
}

@Composable
private fun WidgetContent(weather: WeatherWidgetData.WeatherData) {
    val width = LocalConfiguration.current.screenWidthDp - 20
    val cityNameTextSize = width / 15
    val weatherStatusTextSize = width / 25
    val temperatureVariationTextSize = width / 25
    val temperatureTextSize = width / 10
    val temperatureFeelsTextSize = width / 32
    val iconSize = width / 5
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .padding(start = 10.dp)
        ) {
            Text(
                text = weather.city,
                color = Color.Black,
                fontSize = cityNameTextSize.sp,
                lineHeight = cityNameTextSize.sp
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 10.dp)
            ) {
                GlideImage(
                    modifier = Modifier
                        .size(iconSize.dp)
                        .testTag("weatherIcon"),
                    imageModel = { weather.iconUrl },
                    imageOptions = ImageOptions(contentScale = ContentScale.Inside),
                    loading = {
                        CircularProgressIndicator(
                            modifier = Modifier.testTag("weatherIconLoad"),
                            color = Color.Black
                        )
                    }
                )
                Text(
                    modifier = Modifier,
                    text = weather.weatherStatus,
                    color = Color.Black,
                    fontSize = weatherStatusTextSize.sp,
                    lineHeight = weatherStatusTextSize.sp,
                )
            }

        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = weather.temperatureVariation,
                color = Color.Black,
                fontSize = temperatureVariationTextSize.sp
            )
            Text(
                text = weather.temperature,
                color = Color.Black,
                fontSize = temperatureTextSize.sp
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = stringResource(id = R.string.feels_like) + " ${weather.temperatureFeels}",
                lineHeight = temperatureFeelsTextSize.sp,
                textAlign = TextAlign.Center,
                color = Color.Black,
                fontSize = temperatureFeelsTextSize.sp
            )
        }
    }
}
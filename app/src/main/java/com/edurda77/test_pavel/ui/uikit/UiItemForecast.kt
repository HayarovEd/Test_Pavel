package com.edurda77.test_pavel.ui.uikit

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.edurda77.test_pavel.R
import com.edurda77.test_pavel.domain.model.ItemWeather


@Composable
fun UiItemForecast(
    modifier: Modifier = Modifier,
    itemWeather: ItemWeather,
) {
    Column (
        modifier = modifier
            .padding(10.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(
            modifier = modifier.fillMaxWidth(),
            text = itemWeather.time,
        )
        Spacer(modifier = modifier.height(10.dp))
        Row(
            modifier = modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(
                modifier = modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    modifier = modifier.fillMaxWidth(),
                    model = itemWeather.icon,
                    contentDescription = "",
                    contentScale = ContentScale.FillWidth
                )
                Spacer(modifier = modifier.height(5.dp))
                Text(
                    modifier = modifier,
                    text = itemWeather.description,
                )
            }
            Spacer(modifier = modifier.width(10.dp))
            Column(
                modifier = modifier.weight(2f),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    modifier = modifier,
                    text = "${stringResource(R.string.temp)} ${itemWeather.temp}${stringResource(R.string.temp_unit)}",
                )
                Spacer(modifier = modifier.height(5.dp))
                Text(
                    modifier = modifier,
                    text = "${stringResource(R.string.pressure)} ${itemWeather.pressure} ${stringResource(R.string.pressure_unit)}",
                )
                Spacer(modifier = modifier.height(5.dp))
                Text(
                    modifier = modifier,
                    text = "${stringResource(R.string.humidity)} ${itemWeather.humidity}%"
                )
                Spacer(modifier = modifier.height(5.dp))
                Text(
                    modifier = modifier,
                    text = "${stringResource(R.string.speed_wind)} ${itemWeather.humidity} ${stringResource(R.string.speed_wind_unit)}"
                )
            }
        }
    }
}
package com.edurda77.test_pavel.ui.forecast_screen

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.edurda77.test_pavel.R
import com.edurda77.test_pavel.ui.uikit.UiItemForecast
import com.edurda77.test_pavel.ui.uikit.UiItemForecastLandscape

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForecastScreen(
    modifier: Modifier = Modifier,
    viewModel: ForecastViewModel = hiltViewModel(),
    configuration: Configuration,
    onClickBack:()-> Unit,
) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    val onEvent = viewModel::onEvent

    val snakeBarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current

    LaunchedEffect(key1 = state.value.message) {
        if (state.value.message != null) {
            snakeBarHostState.showSnackbar(
                message = state.value.message!!.asString(context),
                duration = SnackbarDuration.Short
            )
        }
    }
    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.surface,
        snackbarHost = { SnackbarHost(snakeBarHostState) },
        topBar = {
            Box(
                modifier = modifier
                    .padding(top = 50.dp, start = 15.dp, end = 15.dp)
                    .fillMaxWidth(),
            ) {
                IconButton(
                    modifier = modifier.align(Alignment.CenterStart),
                    onClick = onClickBack
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "",
                    )
                }
                Text(
                    modifier = modifier
                        .align(Alignment.Center)
                        .fillMaxWidth(),
                    text = state.value.forecasts?.name?:"",
                    textAlign = TextAlign.Center
                )
            }
        },
    ) { paddings->
        PullToRefreshBox(
            modifier = modifier
                .padding(paddings),
            isRefreshing = state.value.isLoading,
            onRefresh = {
                onEvent(ForecastEvent.OnRefresh)
            },
            indicator = {
                if (state.value.isLoading) {
                    Box(
                        modifier = modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
        ) {
            if (!state.value.forecasts?.itemsWeather.isNullOrEmpty()) {
                if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    LazyRow (
                        modifier = modifier,
                       // horizontalArrangement = Arrangement.spacedBy(15.dp),
                        contentPadding = PaddingValues(
                            start = 15.dp,
                            end = 15.dp,
                            top = 15.dp,
                            bottom = 55.dp
                        ),
                    ) {
                        items(state.value.forecasts?.itemsWeather?: emptyList()) { itemsWeather ->
                            UiItemForecastLandscape(
                                configuration = configuration,
                                itemWeather = itemsWeather,
                            )
                        }
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(15.dp),
                        contentPadding = PaddingValues(
                            start = 15.dp,
                            end = 15.dp,
                            top = 15.dp,
                            bottom = 55.dp
                        )
                    ) {
                        items(state.value.forecasts?.itemsWeather?: emptyList()) { itemsWeather ->
                            UiItemForecast(
                                itemWeather = itemsWeather,
                            )
                        }
                    }
                }
            } else {
                Box(
                    modifier = modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    if (!state.value.isLoading) {
                        Text(
                            modifier = modifier
                                .fillMaxWidth(),
                            text = stringResource(R.string.nothing_search),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}
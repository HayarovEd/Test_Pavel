package com.edurda77.test_pavel.ui.main_screen

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import com.edurda77.test_pavel.ui.navigation.NavigationRoute
import com.edurda77.test_pavel.ui.uikit.UiItemWeatherProvince

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = hiltViewModel(),
    configuration: Configuration,
    onClick: (NavigationRoute.Forecast) -> Unit
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
            Row(
                modifier = modifier
                    .padding(top = 50.dp, start = 15.dp, end = 15.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                OutlinedTextField(
                    modifier = modifier.weight(1f),
                    value = state.value.query,
                    onValueChange = {
                        onEvent(MainEvent.SetQuory(it))
                    },
                    placeholder= {
                        Text(text = stringResource(R.string.search))
                    }
                )
                IconButton(
                    modifier = modifier,
                    enabled = state.value.query.isNotBlank(),
                    onClick = {
                        onEvent(MainEvent.OnSearch)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "",
                    )
                }
            }
        },
    ) { paddings->
        PullToRefreshBox(
            modifier = modifier
                .padding(paddings),
            isRefreshing = state.value.isLoading,
            onRefresh = {
                if (state.value.query.isNotBlank()) {
                    onEvent(MainEvent.OnSearch)
                }},
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
            if (state.value.cities.isNotEmpty()) {
                if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    LazyVerticalGrid(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(15.dp),
                        horizontalArrangement = Arrangement.spacedBy(15.dp),
                        contentPadding = PaddingValues(
                            start = 15.dp,
                            end = 15.dp,
                            top = 15.dp,
                            bottom = 55.dp
                        ),
                        columns = GridCells.Fixed(2)
                    ) {
                        items(state.value.cities) { city ->
                            UiItemWeatherProvince(
                                city = city,
                                onClick = {
                                    onClick(
                                        NavigationRoute.Forecast(
                                            lat = city.lat.toString(),
                                            lon = city.lon.toString()
                                        )
                                    )
                                }
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
                        items(state.value.cities) { city ->
                            UiItemWeatherProvince(
                                city = city,
                                onClick = {
                                    onClick(
                                        NavigationRoute.Forecast(
                                            lat = city.lat.toString(),
                                            lon = city.lon.toString()
                                        )
                                    )
                                }
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
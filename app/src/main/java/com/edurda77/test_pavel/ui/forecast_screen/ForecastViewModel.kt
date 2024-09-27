package com.edurda77.test_pavel.ui.forecast_screen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.edurda77.test_pavel.domain.repository.RemoteRepository
import com.edurda77.test_pavel.domain.utils.ResultWork
import com.edurda77.test_pavel.ui.navigation.NavigationRoute
import com.edurda77.test_pavel.ui.uikit.asUiText
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


@HiltViewModel
class ForecastViewModel @Inject constructor(
    private val remoteRepository: RemoteRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private var _state = MutableStateFlow(ForecastState())
    val state = _state.asStateFlow()


    init {
        getInitialData()
    }


    fun onEvent(event: ForecastEvent) {
        when (event) {
            ForecastEvent.OnRefresh -> {
                viewModelScope.launch {
                    getForecast()
                }
            }
        }
    }

    private fun getInitialData() {
        viewModelScope.launch {
            val lat = savedStateHandle.toRoute<NavigationRoute.Forecast>().lat.toDouble()
            val lon = savedStateHandle.toRoute<NavigationRoute.Forecast>().lon.toDouble()
            _state.value.copy(
                lat = lat,
                lon = lon
            )
                .updateState()
            delay(500)
            getForecast()
        }
    }

    private suspend fun getForecast() {
        _state.value.copy(
           isLoading = true
        )
            .updateState()
        if (state.value.lat!=null&&state.value.lon!=null) {
            when (val result = remoteRepository.getForecast(
                latitude = state.value.lat!!,
                longitude = state.value.lon!!
            )) {
                is ResultWork.Error -> {
                    _state.value.copy(
                        isLoading = false,
                        message = result.error.asUiText()
                    )
                        .updateState()
                }
                is ResultWork.Success -> {
                    _state.value.copy(
                        isLoading = false,
                        forecasts = result.data
                    )
                        .updateState()
                }
            }
        }
    }

    private fun ForecastState.updateState() {
        _state.update {
            this
        }
    }
}
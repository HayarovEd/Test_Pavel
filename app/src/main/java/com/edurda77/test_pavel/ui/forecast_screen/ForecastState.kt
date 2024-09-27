package com.edurda77.test_pavel.ui.forecast_screen

import com.edurda77.resources.uikit.UiText
import com.edurda77.test_pavel.domain.model.Forecast

data class ForecastState(
    val message : UiText? = null,
    val isLoading: Boolean = true,
    val forecasts: Forecast? = null,
    val lat: Double? = null,
    val lon: Double? = null,
)

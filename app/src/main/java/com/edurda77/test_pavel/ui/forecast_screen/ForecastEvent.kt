package com.edurda77.test_pavel.ui.forecast_screen

sealed class ForecastEvent {
    data object OnRefresh : ForecastEvent()
}
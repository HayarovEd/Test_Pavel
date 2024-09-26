package com.edurda77.test_pavel.ui.main_screen

import com.edurda77.resources.uikit.UiText
import com.edurda77.test_pavel.domain.model.WeatherProvince

data class MainState(
    val message : UiText? = null,
    val isLoading: Boolean = false,
    val cities: List<WeatherProvince> = emptyList(),
    val query: String = "",
)

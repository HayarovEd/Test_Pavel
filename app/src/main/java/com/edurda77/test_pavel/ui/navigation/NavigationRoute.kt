package com.edurda77.test_pavel.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class NavigationRoute {
    @Serializable
    data object Main: NavigationRoute()
    @Serializable
    data class Forecast(
        val lat:String,
        val lon:String,
    ): NavigationRoute()
}
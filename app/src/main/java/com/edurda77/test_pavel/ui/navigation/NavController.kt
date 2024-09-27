package com.edurda77.test_pavel.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.edurda77.test_pavel.ui.forecast_screen.ForecastScreen
import com.edurda77.test_pavel.ui.main_screen.MainScreen

@Composable
fun NavController(
    startDestination: NavigationRoute = NavigationRoute.Main,
) {

    val navController = rememberNavController()
    val configuration = LocalConfiguration.current

    NavHost(navController = navController, startDestination = startDestination) {
        composable<NavigationRoute.Main> {
            MainScreen(
                configuration = configuration,
                onClick = {
                    navController.navigate(
                        NavigationRoute.Forecast(
                            lat = it.lat,
                            lon = it.lon
                        )
                    )
                }
            )
        }
        composable<NavigationRoute.Forecast> {
            ForecastScreen(
                configuration = configuration
            )
        }
    }
}
package com.edurda77.test_pavel.domain.model


data class WeatherProvince(
    val name: String,
    val lat: Double,
    val lon: Double,
    val temp: Double,
    val humidity: Int,
    val pressure: Int,
    val precipitation: Precipitation,
)

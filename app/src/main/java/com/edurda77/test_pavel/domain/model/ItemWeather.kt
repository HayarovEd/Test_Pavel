package com.edurda77.test_pavel.domain.model


data class ItemWeather(
    val time:String,
    val pressure: Int,
    val temp: Double,
    val humidity: Int,
    val description: String,
    val icon: String,
)

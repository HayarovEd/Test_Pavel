package com.edurda77.test_pavel.data.remote.geo


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GeoDto(
    @SerialName("country")
    val country: String,
    @SerialName("lat")
    val lat: Double,
    @SerialName("lon")
    val lon: Double,
    @SerialName("name")
    val name: String,
    @SerialName("state")
    val state: String
)
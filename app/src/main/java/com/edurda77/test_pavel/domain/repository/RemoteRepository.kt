package com.edurda77.test_pavel.domain.repository

import com.edurda77.test_pavel.domain.model.Forecast
import com.edurda77.test_pavel.domain.model.WeatherProvince
import com.edurda77.test_pavel.domain.utils.DataError
import com.edurda77.test_pavel.domain.utils.ResultWork

interface RemoteRepository {
    suspend fun getGeoByName(
        query: String,
        limit: Int
    ): ResultWork<List<WeatherProvince>, DataError.Network>

    suspend fun getForecast(
        latitude: Double,
        longitude: Double
    ): ResultWork<List<Forecast>, DataError.Network>
}
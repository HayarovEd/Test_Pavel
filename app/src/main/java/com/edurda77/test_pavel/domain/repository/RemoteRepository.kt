package com.edurda77.test_pavel.domain.repository

import com.edurda77.test_pavel.domain.model.GeoPoint
import com.edurda77.test_pavel.domain.utils.DataError
import com.edurda77.test_pavel.domain.utils.ResultWork

interface RemoteRepository {
    suspend fun getGeoByName(
        query: String,
        limit: Int
    ): ResultWork<List<GeoPoint>, DataError.Network>
}
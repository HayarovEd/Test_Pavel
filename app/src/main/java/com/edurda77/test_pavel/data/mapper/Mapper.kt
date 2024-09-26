package com.edurda77.test_pavel.data.mapper

import com.edurda77.test_pavel.data.remote.geo.GeoDto
import com.edurda77.test_pavel.domain.model.GeoPoint


fun List<GeoDto>.convertToGeos(): List<GeoPoint> {
    return this.map { geoDto ->
        GeoPoint(
            lat = geoDto.lat,
            lon = geoDto.lon,
            name = geoDto.localNames.ru?: geoDto.name
        )
    }
}


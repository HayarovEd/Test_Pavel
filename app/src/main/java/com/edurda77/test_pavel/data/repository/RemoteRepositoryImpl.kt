package com.edurda77.test_pavel.data.repository

import android.net.http.HttpException
import com.edurda77.test_pavel.data.mapper.convertToGeos
import com.edurda77.test_pavel.data.remote.geo.GeoDto
import com.edurda77.test_pavel.domain.model.GeoPoint
import com.edurda77.test_pavel.domain.repository.RemoteRepository
import com.edurda77.test_pavel.domain.utils.APPID
import com.edurda77.test_pavel.domain.utils.BASE_URL
import com.edurda77.test_pavel.domain.utils.DataError
import com.edurda77.test_pavel.domain.utils.GEO_POSTFIX
import com.edurda77.test_pavel.domain.utils.LIMIT
import com.edurda77.test_pavel.domain.utils.QUERY
import com.edurda77.test_pavel.domain.utils.ResultWork
import com.edurda77.test_pavel.domain.utils.WEATHER_KEY
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.client.call.body
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RemoteRepositoryImpl @Inject constructor(
    private val httpClient: HttpClient
): RemoteRepository {

    override suspend fun getGeoByName(
        query: String,
        limit: Int,
    ): ResultWork<List<GeoPoint>, DataError.Network> {
        return withContext(Dispatchers.IO)
        {
            try {
                val result = httpClient.get(BASE_URL + GEO_POSTFIX) {
                    contentType(ContentType.Application.Json)
                    url {
                        parameter(QUERY, query)
                        parameter(LIMIT, limit)
                        parameter(APPID, WEATHER_KEY)
                    }
                }
                    .call
                    .body<List<GeoDto>>()
                ResultWork.Success(result.convertToGeos())
            } catch (e: HttpException) {
                ResultWork.Error(DataError.Network.BAD_REQUEST)
            } catch (e: Exception) {
                ResultWork.Error(DataError.Network.UNKNOWN)
            }
        }
    }
}
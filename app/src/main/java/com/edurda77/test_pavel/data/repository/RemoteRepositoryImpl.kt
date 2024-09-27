package com.edurda77.test_pavel.data.repository

import com.edurda77.test_pavel.data.mapper.convertToWeatherOfProvince
import com.edurda77.test_pavel.data.remote.forecast.ForecastDto
import com.edurda77.test_pavel.data.remote.geo.GeoDto
import com.edurda77.test_pavel.data.remote.weather.WeatherDto
import com.edurda77.test_pavel.domain.model.Forecast
import com.edurda77.test_pavel.domain.model.WeatherProvince
import com.edurda77.test_pavel.domain.repository.RemoteRepository
import com.edurda77.test_pavel.domain.utils.APPID
import com.edurda77.test_pavel.domain.utils.BASE_URL
import com.edurda77.test_pavel.domain.utils.DataError
import com.edurda77.test_pavel.domain.utils.FORECAST_POSTFIX
import com.edurda77.test_pavel.domain.utils.GEO_POSTFIX
import com.edurda77.test_pavel.domain.utils.LANG
import com.edurda77.test_pavel.domain.utils.LATITUDE
import com.edurda77.test_pavel.domain.utils.LIMIT
import com.edurda77.test_pavel.domain.utils.LONGITUDE
import com.edurda77.test_pavel.domain.utils.METRIC
import com.edurda77.test_pavel.domain.utils.QUERY
import com.edurda77.test_pavel.domain.utils.RU_LANG
import com.edurda77.test_pavel.domain.utils.ResultWork
import com.edurda77.test_pavel.domain.utils.UNITS
import com.edurda77.test_pavel.domain.utils.WEATHER_KEY
import com.edurda77.test_pavel.domain.utils.WEATHER_POSTFIX
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.ContentType
import io.ktor.http.contentType
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RemoteRepositoryImpl @Inject constructor(
    private val httpClient: HttpClient
): RemoteRepository {

    override suspend fun getGeoByName(
        query: String,
        limit: Int,
    ): ResultWork<List<WeatherProvince>, DataError.Network> {
        return withContext(Dispatchers.IO)
        {
            try {
                val resultGeo = httpClient.get(BASE_URL + GEO_POSTFIX) {
                    contentType(ContentType.Application.Json)
                    url {
                        parameter(QUERY, query)
                        parameter(LIMIT, limit)
                        parameter(APPID, WEATHER_KEY)
                    }
                }
                    .call
                    .body<List<GeoDto>>()
                val weatherOfProvincies = mutableListOf<WeatherProvince>()
                resultGeo.forEach { currentGeo->
                    val weather = httpClient.get(BASE_URL + WEATHER_POSTFIX) {
                        contentType(ContentType.Application.Json)
                        url {
                            parameter(LATITUDE, currentGeo.lat)
                            parameter(LONGITUDE, currentGeo.lon)
                            parameter(APPID, WEATHER_KEY)
                            parameter(UNITS, METRIC)
                            parameter(LANG, RU_LANG)
                        }
                    }
                        .call
                        .body<WeatherDto>()
                    weatherOfProvincies.add(weather.convertToWeatherOfProvince())
                }
                ResultWork.Success(weatherOfProvincies)
            } catch (e: ClientRequestException) {
                ResultWork.Error(DataError.Network.BAD_REQUEST)
            } catch (e: ServerResponseException) {
                ResultWork.Error(DataError.Network.SERVER_ERROR)
            } catch (e: Exception) {
                ResultWork.Error(DataError.Network.UNKNOWN)
            }
        }
    }

    override suspend fun getForecast(
        latitude: Double,
        longitude: Double,
    ): ResultWork<Forecast, DataError.Network> {
        return withContext(Dispatchers.IO)
        {
            try {
                val weather = httpClient.get(BASE_URL + FORECAST_POSTFIX) {
                    contentType(ContentType.Application.Json)
                    url {
                        parameter(LATITUDE, latitude)
                        parameter(LONGITUDE, longitude)
                        parameter(APPID, WEATHER_KEY)
                        parameter(UNITS, METRIC)
                        parameter(LANG, RU_LANG)
                    }
                }
                    .call
                    .body<ForecastDto>()
                ResultWork.Success(weather.convertToWeatherOfProvince())
            } catch (e: ClientRequestException) {
                ResultWork.Error(DataError.Network.BAD_REQUEST)
            } catch (e: ServerResponseException) {
                ResultWork.Error(DataError.Network.SERVER_ERROR)
            } catch (e: Exception) {
                ResultWork.Error(DataError.Network.UNKNOWN)
            }
        }
    }
}
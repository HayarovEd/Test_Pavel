package com.edurda77.test_pavel.data.mapper

import com.edurda77.test_pavel.data.remote.forecast.ForecastDto
import com.edurda77.test_pavel.data.remote.weather.WeatherDto
import com.edurda77.test_pavel.domain.model.Forecast
import com.edurda77.test_pavel.domain.model.ItemWeather
import com.edurda77.test_pavel.domain.model.Precipitation
import com.edurda77.test_pavel.domain.model.WeatherProvince
import com.edurda77.test_pavel.domain.utils.BASE_IMAGE_URL
import com.edurda77.test_pavel.domain.utils.ICON_POSTFIX
import com.edurda77.test_pavel.domain.utils.ICON_PREFIX
import com.edurda77.test_pavel.domain.utils.convertHectopascalToMillimetersOfMercury
import com.edurda77.test_pavel.domain.utils.convertLongToTime


fun WeatherDto.convertToForecast(): WeatherProvince {
    return WeatherProvince(
        lat = this.coord.lat,
        lon = this.coord.lon,
        name = this.name,
        humidity = this.main.humidity,
        precipitation = Precipitation(
            description = this.weather.first().description,
            icon = "$BASE_IMAGE_URL$ICON_PREFIX${this.weather.first().icon}$ICON_POSTFIX",
            main = this.weather.first().main
        ),
        pressure = convertHectopascalToMillimetersOfMercury(this.main.pressure),
        temp = this.main.temp
    )

}

fun ForecastDto.convertToForecast(): Forecast {
    return Forecast(
        name = this.city.name,
        itemsWeather = this.list.map {
            ItemWeather(
                time = convertLongToTime(it.dt.toLong()),
                pressure = convertHectopascalToMillimetersOfMercury(it.main.pressure),
                description = it.weather.first().description,
                humidity = it.main.humidity,
                icon = "$BASE_IMAGE_URL$ICON_PREFIX${it.weather.first().icon}$ICON_POSTFIX",
                temp = it.main.temp,
                windSpeed = it.wind.speed
            )
        }
    )

}

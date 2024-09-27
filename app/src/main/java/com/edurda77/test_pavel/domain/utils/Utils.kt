package com.edurda77.test_pavel.domain.utils

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

fun convertLongToTime (time: Long): String {
    val date = Instant
        .ofEpochMilli(time)
        .atZone(ZoneId.systemDefault())
        .toLocalDateTime()
    val formatter = DateTimeFormatter.ofPattern(SAMPLE_DATE)
    return formatter.format(date)
}

fun convertHectopascalToMillimetersOfMercury(hectopascal: Int): Int {
    val conversionFactor = 0.7500617528128
    return (hectopascal * conversionFactor).toInt()
}
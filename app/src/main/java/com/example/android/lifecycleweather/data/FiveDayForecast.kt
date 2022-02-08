package com.example.android.lifecycleweather.data

import com.squareup.moshi.Json

data class FiveDayForecast(
    @Json(name = "list") val periods: List<ForecastPeriod>,
    val city: ForecastCity
)

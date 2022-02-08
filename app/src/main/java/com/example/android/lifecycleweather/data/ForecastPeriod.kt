package com.example.android.lifecycleweather.data

import com.squareup.moshi.FromJson
import java.io.Serializable

data class ForecastPeriod(
    val epoch: Int,
    val highTemp: Int,
    val lowTemp: Int,
    val pop: Int,
    val cloudCover: Int,
    val windSpeed: Int,
    val windDirDeg: Int,
    val description: String,
    val iconUrl: String
) : Serializable

/* ******************************************************************************************
 * Below is a set of classes used to parse the JSON response from the OpenWeather API into
 * a ForecastPeriod object.  The first several classes are designed to match the structure
 * of one element of the `list` field in the OpenWeather 5-day forecast API's JSON response.
 * The last is a custom type adapter that can be used with Moshi to parse OpenWeather JSON
 * directly into a ForecastPeriod object.
 * ******************************************************************************************/

data class OpenWeatherListJson(
    val dt: Int,
    val pop: Double,
    val main: OpenWeatherListMainJson,
    val clouds: OpenWeatherListCloudsJson,
    val wind: OpenWeatherListWindJson,
    val weather: List<OpenWeatherListWeatherJson>
)

data class OpenWeatherListMainJson(
    val temp_min: Double,
    val temp_max: Double
)

data class OpenWeatherListCloudsJson(
    val all: Int
)

data class OpenWeatherListWindJson(
    val speed: Double,
    val deg: Int
)

data class OpenWeatherListWeatherJson(
    val description: String,
    val icon: String
)

class OpenWeatherListJsonAdapter {
    @FromJson
    fun forecastPeriodFromJson(list: OpenWeatherListJson) = ForecastPeriod(
        epoch = list.dt,
        highTemp = list.main.temp_max.toInt(),
        lowTemp = list.main.temp_min.toInt(),
        pop = (list.pop * 100).toInt(),
        cloudCover = list.clouds.all,
        windSpeed = list.wind.speed.toInt(),
        windDirDeg = list.wind.deg,
        description = list.weather[0].description,
        iconUrl = "https://openweathermap.org/img/wn/${list.weather[0].icon}@4x.png"
    )
}
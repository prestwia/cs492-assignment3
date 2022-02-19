package com.example.android.lifecycleweather.api

import com.example.android.lifecycleweather.data.FiveDayForecast
import com.example.android.lifecycleweather.data.OpenWeatherCityJsonAdapter
import com.example.android.lifecycleweather.data.OpenWeatherListJsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

// "https://api.openweathermap.org/data/2.5/forecast?"
// "${apiBaseUrl}q=Corvallis,Oregon&units=imperial&appid=05daa70b4de95831930f7b0b507e9564"
interface ForecastService {
    @GET("forecast?/data/2.5/forecast")
    suspend fun getForecast(
        @Query("q") location: String,
        @Query("units") units: String,
        @Query("appid") apiKey: String = "05daa70b4de95831930f7b0b507e9564"
    ): FiveDayForecast

    companion object {
        private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
        fun create(): ForecastService {
            val moshi = Moshi.Builder()
                .add(OpenWeatherListJsonAdapter())
                .add(OpenWeatherCityJsonAdapter())
                .addLast(KotlinJsonAdapterFactory())
                .build()
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build()
                .create(ForecastService::class.java)
        }
    }
}

package com.example.android.lifecycleweather.data

import com.example.android.lifecycleweather.api.ForecastService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ForecastRepository(
    private val service: ForecastService,
    private val ioDispatcher: CoroutineDispatcher =
        Dispatchers.IO
) {
    suspend fun loadForecast(query: String): Result<FiveDayForecast> =
        withContext(ioDispatcher) {
            try {
                val results = service.getForecast(query, "imperial")
                Result.success(results)
            } catch(e: Exception) {
                Result.failure(e)
            }

    }
}

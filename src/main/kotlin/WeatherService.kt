package com.meteorology

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.redisson.api.RList
import org.redisson.api.RMapCache
import org.redisson.api.RedissonClient
import java.util.concurrent.TimeUnit

class WeatherService(private val client: HttpClient, redissonClient: RedissonClient) {
    private val weatherCache: RMapCache<String, String> = redissonClient.getMapCache("weatherCache")
    private val logList: RList<String> = redissonClient.getList("logs")

    init {
        startCron()
    }

    suspend fun getWeatherData( query: String): String {
        val country = query.lowercase()

        weatherCache[country]?.let {
            return it
        }

        repeat(3) {
            try {
                if (Math.random() < 0.2) throw Error("The API Request Failed")
                val response: HttpResponse = client.get("https://api.weatherstack.com/current") {
                    parameter("access_key", "7d40e3d75898d4c12d5c1ff07cba9b12")
                    parameter("query", country)
                }
                val responseText: String = response.bodyAsText()
                weatherCache[country] = responseText
                return responseText
            } catch (e: Exception) {
                logError(e)
                if (it == 2) throw e
            }
        }
        throw Error("Failed to get weather data after 3 attempts")
    }

    private fun logError(e: Exception) {
        val timestamp = System.currentTimeMillis()
        val logMessage = "Error: ${e.message}, Timestamp: $timestamp"
        logList.add(logMessage)
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun startCron(){
        GlobalScope.launch {
            val keys = listOf("santiago (cl)", "zúrich (ch)", "auckland (nz)", "sídney (au)", "londres (uk)", "georgia (usa)")
            for (key in keys) {
                weatherCache[key] = getWeatherData(key)
            }

            while (true) {
                delay(TimeUnit.MINUTES.toMillis(5))
                updateCache()
            }
        }
    }

    private suspend fun updateCache() {
        val allKeys = weatherCache.keys
        for(key in allKeys) {
            val value = getWeatherData(key)
            weatherCache[key] = value
        }

    }
}
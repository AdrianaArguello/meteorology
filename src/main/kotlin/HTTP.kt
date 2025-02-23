package com.meteorology

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.redisson.Redisson
import org.redisson.api.RedissonClient
import org.redisson.config.Config

fun Application.configureHTTP() {

    fun createRedissonClient(): RedissonClient {
        val config = Config()
        "redis://redis:6379".also { config.useSingleServer().address = it }
        return Redisson.create(config)
    }

    val client = HttpClient(CIO)
    val redissonClient = createRedissonClient()
    val weatherService = WeatherService(client, redissonClient)

    routing {
        get("/weather") {
            val query = call.request.queryParameters["query"] ?: error("Missing query")
            call.respond(weatherService.getWeatherData(query))
        }
    }
}

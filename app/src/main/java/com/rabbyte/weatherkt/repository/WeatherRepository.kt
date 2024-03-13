package com.rabbyte.weatherkt.repository

import com.rabbyte.weatherkt.server.ApiServices

class WeatherRepository(private val apiServices: ApiServices) {

    fun getCurrentWeather(lat: Double, lng: Double, unit: String) =
        apiServices.getCurrentWeather(lat, lng, unit, "2cb67549db9dbdce3a89b65569d1e34f")

    fun getForecastWeather(lat: Double, lng: Double, unit: String) =
        apiServices.getForecastWeather(lat, lng, unit, "2cb67549db9dbdce3a89b65569d1e34f")
}
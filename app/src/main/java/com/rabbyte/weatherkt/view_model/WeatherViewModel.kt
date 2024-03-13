package com.rabbyte.weatherkt.view_model

import androidx.lifecycle.ViewModel
import com.rabbyte.weatherkt.repository.WeatherRepository
import com.rabbyte.weatherkt.server.ApiClient
import com.rabbyte.weatherkt.server.ApiServices

class WeatherViewModel(private val repository: WeatherRepository) : ViewModel() {

    constructor() : this(WeatherRepository(ApiClient().getClient().create(ApiServices::class.java)))

    fun loadCurrentWeather(lat: Double, lng: Double, units: String) =
        repository.getCurrentWeather(lat, lng, units)

    fun loadForecastWeather(lat: Double, lng: Double, units: String) =
        repository.getForecastWeather(lat, lng, units)
}
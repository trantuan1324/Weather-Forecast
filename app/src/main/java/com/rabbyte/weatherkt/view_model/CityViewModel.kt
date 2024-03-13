package com.rabbyte.weatherkt.view_model

import androidx.lifecycle.ViewModel
import com.rabbyte.weatherkt.repository.CityRepository
import com.rabbyte.weatherkt.server.ApiClient
import com.rabbyte.weatherkt.server.ApiServices

class CityViewModel(private val repository: CityRepository) : ViewModel() {

    constructor() : this(CityRepository(ApiClient().getClient().create(ApiServices::class.java)))

    fun loadCity(q: String, limit: Int) = repository.getCities(q, limit)
}
package com.rabbyte.weatherkt.repository

import com.rabbyte.weatherkt.server.ApiServices

class CityRepository(private val api: ApiServices) {
    fun getCities(q: String, limit: Int) =
        api.getCitiesList(q, limit, "2cb67549db9dbdce3a89b65569d1e34f")

}
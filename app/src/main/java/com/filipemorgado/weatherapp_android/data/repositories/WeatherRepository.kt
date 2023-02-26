package com.filipemorgado.weatherapp_android.data.repositories

import com.filipemorgado.weatherapp_android.data.network.ApiInterface
import com.filipemorgado.weatherapp_android.data.network.SafeApiRequest

class WeatherRepository(
    private val api: ApiInterface,
) : SafeApiRequest() {

    suspend fun findCityWeatherByName(cityName: String) {

    }

}
package com.filipemorgado.weatherapp_android.data.model

data class CurrentWeatherDetails(
    val temp_c: Double,
    val humidity: Int,
    val windKph: Double,
    val conditionInfo: String,
    val icon: Int,
)

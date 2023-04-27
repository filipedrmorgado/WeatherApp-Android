package com.filipemorgado.weatherapp_android.data.model.response

data class GeoDBCitiesResponse(
    val data: List<GeoDBCitiesData>
)

data class GeoDBCitiesData(
    val city: String,
    val country: String,
    val latitude: Double,
    val longitude: Double
)

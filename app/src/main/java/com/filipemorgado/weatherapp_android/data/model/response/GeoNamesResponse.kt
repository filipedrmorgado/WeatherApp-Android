package com.filipemorgado.weatherapp_android.data.model.response

data class GeoNamesResponse(
    val geonames: List<GeoName>
)

data class GeoName(
    val name: String,
    val countryName: String,
    val lat: Double,
    val lng: Double
)

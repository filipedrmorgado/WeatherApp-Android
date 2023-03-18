package com.filipemorgado.weatherapp_android.data.model.response

/**
 * Forecast for multiple days dataclass to receive data from the API response
 */
data class RealtimeForecastDataResponse(
    val location: Location,
    val current: Current
) {
    data class Location(
        val name: String,
        val region: String,
        val country: String,
        val lat: Double,
        val lon: Double,
        val tzId: String,
        val localtimeEpoch: Long,
        val localtime: String
    )

    data class Current(
        val lastUpdatedEpoch: Long,
        val lastUpdated: String,
        val tempC: Double,
        val tempF: Double,
        val isDay: Int,
        val condition: Condition,
        val windMph: Double,
        val windKph: Double,
        val windDegree: Int,
        val windDir: String,
        val pressureMb: Double,
        val pressureIn: Double,
        val precipMm: Double,
        val precipIn: Double,
        val humidity: Int,
        val cloud: Int,
        val feelsLikeC: Double,
        val feelsLikeF: Double,
        val visKm: Double,
        val visMiles: Double,
        val uv: Double,
        val gustMph: Double,
        val gustKph: Double
    ) {
        data class Condition(
            val text: String,
            val icon: String,
            val code: Int
        )
    }
}

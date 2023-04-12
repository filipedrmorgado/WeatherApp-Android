package com.filipemorgado.weatherapp_android.data.model.response

import java.io.Serializable

/**
 * Next day dataclass to receive data from the API response
 */
data class NextDaysForecastResponse(
    val location: LocationData,
    val current: CurrentData,
    val forecast: ForecastData
) : Serializable

data class LocationData(
    val name: String,
    val region: String,
    val country: String,
    val lat: Double,
    val lon: Double,
    val tz_id: String,
    val localtime_epoch: Long,
    val localtime: String
)

data class CurrentData(
    val last_updated_epoch: Long,
    val last_updated: String,
    val temp_c: Double,
    val temp_f: Double,
    val is_day: Int,
    val condition: ConditionData,
    val wind_mph: Double,
    val wind_kph: Double,
    val wind_degree: Int,
    val wind_dir: String,
    val pressure_mb: Double,
    val pressure_in: Double,
    val precip_mm: Double,
    val precip_in: Double,
    val humidity: Int,
    val cloud: Int,
    val feelslike_c: Double,
    val feelslike_f: Double,
    val vis_km: Double,
    val vis_miles: Double,
    val uv: Double,
    val gust_mph: Double,
    val gust_kph: Double
)

data class ForecastData(
    val forecastday: List<ForecastDayData>
)

data class ForecastDayData(
    val date: String,
    val date_epoch: Long,
    val day: DayData,
    val astro: AstroData,
    val hour: List<HourData>
) : Serializable

data class DayData(
    val maxtemp_c: Double,
    val maxtemp_f: Double,
    val mintemp_c: Double,
    val mintemp_f: Double,
    val avgtemp_c: Double,
    val avgtemp_f: Double,
    val maxwind_mph: Double,
    val maxwind_kph: Double,
    val totalprecip_mm: Double,
    val totalprecip_in: Double,
    val totalsnow_cm: Double,
    val avgvis_km: Double,
    val avgvis_miles: Double,
    val avghumidity: Double,
    val daily_will_it_rain: Int,
    val daily_chance_of_rain: Int,
    val daily_will_it_snow: Int,
    val daily_chance_of_snow: Int,
    val condition: ConditionData,
    val uv: Double
)

data class AstroData(
    val sunrise: String,
    val sunset: String,
    val moonrise: String,
    val moonset: String,
    val moon_phase: String,
    val moon_illumination: String,
    val is_moon_up: Int,
    val is_sun_up: Int
)

data class HourData(
   val timeEpoch: Long,
   val time: String,
   val tempC: Double,
   val tempF: Double,
   val isDay: Int,
   val condition: ConditionData,
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
   val feelslikeC: Double,
   val feelslikeF: Double,
   val windchillC: Double,
   val windchillF: Double,
   val heatindexC: Double,
   val heatindexF: Double,
   val dewpointC: Double,
   val dewpointF: Double,
   val willItRain: Int,
   val chanceOfRain: Int,
   val willItSnow: Int,
   val chanceOfSnow: Int,
   val visKm: Double,
   val visMiles: Double,
   val gustMph: Double,
   val gustKph: Double,
   val uv: Double
)

data class ConditionData(
    val text: String,
    val icon: String,
    val code: Int
)
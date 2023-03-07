package com.filipemorgado.weatherapp_android.data.model.response

import com.google.gson.annotations.SerializedName

data class NextDaysForecastResponse(
    @SerializedName("location") val location: Location,
    @SerializedName("current") val current: Current,
    @SerializedName("forecast") val forecast: Forecast
)

data class Location(
    @SerializedName("name") val name: String,
    @SerializedName("region") val region: String,
    @SerializedName("country") val country: String,
    @SerializedName("lat") val lat: Double,
    @SerializedName("lon") val lon: Double,
    @SerializedName("tz_id") val tzId: String,
    @SerializedName("localtime_epoch") val localtimeEpoch: Long,
    @SerializedName("localtime") val localtime: String
)

data class Current(
    @SerializedName("last_updated_epoch") val lastUpdatedEpoch: Long,
    @SerializedName("last_updated") val lastUpdated: String,
    @SerializedName("temp_c") val tempC: Double,
    @SerializedName("temp_f") val tempF: Double,
    @SerializedName("is_day") val isDay: Int,
    @SerializedName("condition") val condition: Condition,
    @SerializedName("wind_mph") val windMph: Double,
    @SerializedName("wind_kph") val windKph: Double,
    @SerializedName("wind_degree") val windDegree: Int,
    @SerializedName("wind_dir") val windDir: String,
    @SerializedName("pressure_mb") val pressureMb: Double,
    @SerializedName("pressure_in") val pressureIn: Double,
    @SerializedName("precip_mm") val precipMm: Double,
    @SerializedName("precip_in") val precipIn: Double,
    @SerializedName("humidity") val humidity: Int,
    @SerializedName("cloud") val cloud: Int,
    @SerializedName("feelslike_c") val feelslikeC: Double,
    @SerializedName("feelslike_f") val feelslikeF: Double,
    @SerializedName("vis_km") val visKm: Double,
    @SerializedName("vis_miles") val visMiles: Double,
    @SerializedName("uv") val uv: Double,
    @SerializedName("gust_mph") val gustMph: Double,
    @SerializedName("gust_kph") val gustKph: Double
)

data class Forecast(
    @SerializedName("forecastday") val forecastDay: List<ForecastDay>
)

data class ForecastDay(
    @SerializedName("date") val date: String,
    @SerializedName("date_epoch") val dateEpoch: Long,
    @SerializedName("day") val day: Day
)

data class Day(
    @SerializedName("maxtemp_c") val maxtempC: Double,
    @SerializedName("maxtemp_f") val maxtempF: Double,
    @SerializedName("mintemp_c") val mintempC: Double,
    @SerializedName("mintemp_f") val mintempF: Double,
    @SerializedName("avgtemp_c") val avgtempC: Double,
    @SerializedName("avgtemp_f") val avgtempF: Double,
    @SerializedName("maxwind_mph") val maxwindMph: Double,
    @SerializedName("maxwind_kph") val maxwindKph: Double,
    @SerializedName("totalprecip_mm") val totalprecipMm: Double,
    @SerializedName("totalprecip_in") val totalprecipIn: Double,
    @SerializedName("avgvis_km") val avgvisKm: Double,
    @SerializedName("avgvis_miles") val avgvisMiles: Double,
    @SerializedName("avghumidity") val avghumidity: Int,
    @SerializedName("uv") val uv: Double
)

data class Condition(
    @SerializedName("text") val text: String,
    @SerializedName("icon") val icon: String,
    @SerializedName("code") val code: Int
)
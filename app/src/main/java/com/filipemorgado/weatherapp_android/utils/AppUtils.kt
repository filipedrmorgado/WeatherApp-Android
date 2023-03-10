package com.filipemorgado.weatherapp_android.utils

import com.filipemorgado.weatherapp_android.R

object AppUtils {

    /**
     * Get animation file according to weather status code
     *
     * @param weatherCode int weather status code
     * @return id of animation json file
     */
    fun getWeatherAnimation(weatherCode: Int): Int {
        return when (weatherCode) {
            1000 -> R.raw.clear_day
            1003 -> R.raw.few_clouds
            1006, 1030, 1069 -> R.raw.cloudy_weather
            1009 -> R.raw.broken_clouds
            1087, 1273, 1276, 1279, 1282 -> R.raw.storm_weather
            1135, 1147 -> R.raw.mostly_cloudy
            1063, 1072, 1150, 1153, 1168, 1183, 1186, 1198,
            1204, 1240, 1249 -> R.raw.rainy_weather
            1089, 1192, 1195, 1207, 1243, 1246, 1252 -> R.raw.shower_rain
            1066, 1114, 1117, 1171, 1201, 1210, 1213, 1216,
            1219, 1222, 1225, 1237, 1255, 1258, 1261, 1264 -> R.raw.snow_weather
            else -> R.raw.unknown
        }
    }

    val DAYS_OF_WEEK = arrayOf(
        "Sunday",
        "Monday",
        "Tuesday",
        "Wednesday",
        "Thursday",
        "Friday",
        "Saturday"
    )

    val RECYCLER_COLORS = arrayOf(
        R.color.babyGreen,
        R.color.colorAccent,
        R.color.orange,
        R.color.babyBlue,
        R.color.red,
    )

}
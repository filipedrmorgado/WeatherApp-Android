package com.filipemorgado.weatherapp_android.utils


const val RECYCLER_SIZE = 5
const val ALPHA_RECYCLER_SHADOW_COLOR = 64 // 25% Opacity

// API constants
const val CONNECT_TIMEOUT = 1L
const val READ_TIMEOUT = 60L
const val WRITE_TIMEOUT = 60L

// Default Values
const val DEFAULT_CITY = "Coimbra"

// LineChart constants
const val LINE_CHART_LINE_WIDTH = 4F
const val LINE_CHART_CIRCLE_RADIUS = 7F
const val LINE_CHART_DATASET_DESCRIPTION_TEXT_SIZE = 12F
const val LINE_CHART_ANIMATION_TIME = 1000

// Shared preferences
const val SHARED_PREF_LATEST_CURRENT_WEATHER = "latestCurrentWeatherForecast"
const val SHARED_PREF_LATEST_FORECAST_WEATHER = "latestNextDaysForecast"
const val SHARED_PREF_LATEST_REQUESTED_CITY = "latestRequestedCity"

//todo list
// check the order of time on details bottom sheet
// Review logic when retrieving data from repo. Just return the result on the wanted object, without response.
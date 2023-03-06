package com.filipemorgado.weatherapp_android.data.repositories

import android.util.Log
import com.filipemorgado.weatherapp_android.data.model.response.RealtimeForecastDataResponse
import com.filipemorgado.weatherapp_android.data.network.ApiInterface
import com.filipemorgado.weatherapp_android.data.network.SafeApiRequest
import com.filipemorgado.weatherapp_android.utils.ApiException
import com.filipemorgado.weatherapp_android.utils.NoInternetException

class WeatherRepository(
    private val api: ApiInterface,
) : SafeApiRequest() {

    suspend fun findCityWeatherByName(cityName: String): Result<RealtimeForecastDataResponse> {
        return try {
            val result = apiRequest { api.findCityWeatherData(cityName = cityName) }
            Log.d("WeatherRepository", "findCityWeatherByName: result=$result")

            // Request was sucessfull
            return Result.success(result)
        } catch (e: ApiException) {
            Log.e("WeatherRepository", "findCityWeatherByName: ApiException=${e.message}")
            Result.failure(e)
        } catch (e: NoInternetException) {
            Log.e("WeatherRepository", "findCityWeatherByName: NoInternetException=${e.message}")
            Result.failure(e)
        } catch (e: Exception) {
            Log.e("WeatherRepository", "findCityWeatherByName: Exception=${e.message}")
            Result.failure(e)
        }
    }

    suspend fun fourDaysForecast(cityName: String): Result<RealtimeForecastDataResponse> {
        return try {
            val result = apiRequest { api.fourDaysForecast(cityName) }
            Log.d("WeatherRepository", "findCityWeatherByName: result=$result")

            // Request was sucessfull
            return Result.success(result)
        } catch (e: ApiException) {
            Log.e("WeatherRepository", "findCityWeatherByName: ApiException=${e.message}")
            Result.failure(e)
        } catch (e: NoInternetException) {
            Log.e("WeatherRepository", "findCityWeatherByName: NoInternetException=${e.message}")
            Result.failure(e)
        } catch (e: Exception) {
            Log.e("WeatherRepository", "findCityWeatherByName: Exception=${e.message}")
            Result.failure(e)
        }
    }

}
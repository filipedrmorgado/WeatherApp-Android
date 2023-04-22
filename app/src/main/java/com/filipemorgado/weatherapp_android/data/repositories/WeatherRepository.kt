package com.filipemorgado.weatherapp_android.data.repositories

import android.util.Log
import com.filipemorgado.weatherapp_android.data.model.response.NextDaysForecastResponse
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

            // Request was successful
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

    suspend fun getCityNextDaysForecast(cityName: String): Result<NextDaysForecastResponse> {
        return try {
            val result = apiRequest { api.getCityNextDaysForecast(cityName = cityName) }
            Log.d("WeatherRepository", "getCityNextDaysForecast: result=$result")


            // Request was successful
            return Result.success(result)
        } catch (e: ApiException) {
            Log.e("WeatherRepository", "getCityNextDaysForecast: ApiException=${e.message}")
            Result.failure(e)
        } catch (e: NoInternetException) {
            Log.e("WeatherRepository", "getCityNextDaysForecast: NoInternetException=${e.message}")
            Result.failure(e)
        } catch (e: Exception) {
            Log.e("WeatherRepository", "getCityNextDaysForecast: Exception=${e.message}")
            Result.failure(e)
        }
    }

}
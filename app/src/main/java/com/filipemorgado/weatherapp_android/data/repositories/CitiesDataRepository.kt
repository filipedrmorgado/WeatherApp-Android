package com.filipemorgado.weatherapp_android.data.repositories

import android.util.Log
import com.filipemorgado.weatherapp_android.data.model.response.GeoNamesResponse
import com.filipemorgado.weatherapp_android.data.network.CitiesDataApiInterface
import com.filipemorgado.weatherapp_android.data.network.SafeApiRequest
import com.filipemorgado.weatherapp_android.utils.ApiException
import com.filipemorgado.weatherapp_android.utils.NoInternetException

class CitiesDataRepository(
    private val api: CitiesDataApiInterface,
) : SafeApiRequest() {

    suspend fun findCityWeatherByName(cityName: String): Result<GeoNamesResponse> {
        return try {
            val result = apiRequest { api.searchCities(name = cityName) }
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
}
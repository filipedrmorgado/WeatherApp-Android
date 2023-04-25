package com.filipemorgado.weatherapp_android.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.filipemorgado.weatherapp_android.data.model.response.NextDaysForecastResponse
import com.filipemorgado.weatherapp_android.data.model.response.RealtimeForecastDataResponse
import com.filipemorgado.weatherapp_android.data.repositories.CitiesDataRepository
import com.filipemorgado.weatherapp_android.data.repositories.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WeatherViewModel(
    private val weatherRepository: WeatherRepository,
    private val citiesDataRepository: CitiesDataRepository,
    ) : ViewModel() {

    // Flow that keeps up to date the state of the current weather request and its result, success or error.
    private val _currentWeatherFlow = MutableSharedFlow<RealtimeForecastDataResponse>()
    val currentWeatherFlow = _currentWeatherFlow.asSharedFlow()

    private val _forecastWeatherFlow = MutableSharedFlow<NextDaysForecastResponse>()
    val forecastWeatherFlow = _forecastWeatherFlow.asSharedFlow()

    private var _currentWeather: RealtimeForecastDataResponse? = null
    val currentWeather: RealtimeForecastDataResponse?
        get() = _currentWeather

    private var _forecastWeather: NextDaysForecastResponse? = null
    val forecastWeather: NextDaysForecastResponse?
        get() = _forecastWeather

    init {
        // Request data from OpenWeather API
        //todo make it the saved city data to be requested
        viewModelScope.launch(Dispatchers.IO) {
            val currentWeatherDeferred = async { findCityWeatherByName("Paris") }
            val forecastDeferred = async { getCityNextDaysForecast("Paris") }

            //  Ensures that the shared flows _currentWeatherFlow and _forecastWeatherFlow are both updated
            //  with the latest data before the UI can start observing them.
            currentWeatherDeferred.await()
            forecastDeferred.await()
        }
    }

    /**
     * Gets the current weather by city name
     */
    private suspend fun findCityWeatherByName(cityName: String) = withContext(Dispatchers.IO) {
        //todo demonstrate a loading while requesting
        val result = weatherRepository.findCityWeatherByName(cityName)
        if(result.isFailure) {
            Log.e("MainFragment", "currentWeatherUpdate: Error retrieving data.")
            return@withContext
        }
        val responseData = result.getOrNull()
        if(responseData == null) {
            Log.e("MainFragment", "currentWeatherUpdate: responseData is null.")
            return@withContext
        }
        // Trigger observers
        _currentWeatherFlow.emit(responseData)
        _currentWeather = result.getOrNull()
    }

    /**
     * Gets the forecast for the next 4 days
     */
    private suspend fun getCityNextDaysForecast(cityName: String) = withContext(Dispatchers.IO) {
        //todo demonstrate a loading while requesting
        val result = weatherRepository.getCityNextDaysForecast(cityName)

        if(result.isFailure) {
            Log.e("MainFragment", "currentWeatherUpdate: Error retrieving data.")
            return@withContext
        }
        val responseData = result.getOrNull()
        if(responseData == null) {
            Log.e("MainFragment", "currentWeatherUpdate: responseData is null.")
            return@withContext
        }
        // Trigger observers
        _forecastWeatherFlow.emit(responseData)
        _forecastWeather = result.getOrNull()
    }


}
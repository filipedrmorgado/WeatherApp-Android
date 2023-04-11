package com.filipemorgado.weatherapp_android.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.filipemorgado.weatherapp_android.data.model.response.NextDaysForecastResponse
import com.filipemorgado.weatherapp_android.data.model.response.RealtimeForecastDataResponse
import com.filipemorgado.weatherapp_android.data.repositories.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WeatherViewModel(private val weatherRepository: WeatherRepository) : ViewModel() {

    // Flow that keeps up to date the state of the current weather request and its result, success or error.
    private val _currentWeatherFlow = MutableSharedFlow<Result<RealtimeForecastDataResponse>>()
    val currentWeatherFlow = _currentWeatherFlow.asSharedFlow()

    private val _forecastWeatherFlow = MutableSharedFlow<Result<NextDaysForecastResponse>>()
    val forecastWeatherFlow = _forecastWeatherFlow.asSharedFlow()

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
    private suspend fun findCityWeatherByName(cityName: String) = withContext(Dispatchers.IO)  {
        //todo demonstrate a loading while requesting
            val result = weatherRepository.findCityWeatherByName(cityName)
            _currentWeatherFlow.emit(result)
            Log.i("WeatherViewModel", "findCityWeatherByName: result=$result")
    }

    /**
     * Gets the forecast for the next 4 days
     */
    private suspend fun getCityNextDaysForecast(cityName: String) = withContext(Dispatchers.IO)  {
        //todo demonstrate a loading while requesting
        val result = weatherRepository.getCityNextDaysForecast(cityName)
        _forecastWeatherFlow.emit(result)
        Log.i("WeatherViewModel", "getCityNextDaysForecast: result=$result")
    }


}
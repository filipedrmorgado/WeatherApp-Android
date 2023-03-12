package com.filipemorgado.weatherapp_android.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.filipemorgado.weatherapp_android.data.model.response.NextDaysForecastResponse
import com.filipemorgado.weatherapp_android.data.model.response.RealtimeForecastDataResponse
import com.filipemorgado.weatherapp_android.data.repositories.WeatherRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class WeatherViewModel(private val weatherRepository: WeatherRepository) : ViewModel() {

    // Flow that keeps up to date the state of the current weather request and its result, success or error.
    private val _currentWeatherFlow = MutableSharedFlow<Result<RealtimeForecastDataResponse>>()
    val currentWeatherFlow = _currentWeatherFlow.asSharedFlow()

    private val _forecastWeatherFlow = MutableSharedFlow<Result<NextDaysForecastResponse>>()
    val forecastWeatherFlow = _forecastWeatherFlow.asSharedFlow()

    init {
        // Request data from OpenWeather API
        //todo make it the saved city data to be requested
        findCityWeatherByName("Paris")
        getCityNextDaysForecast("Paris")
    }

    /**
     * Gets the current weather by city name
     */
    private fun findCityWeatherByName(cityName: String) {
        //todo demonstrate a loading while requesting
        viewModelScope.launch {
            val result = weatherRepository.findCityWeatherByName(cityName)
            _currentWeatherFlow.emit(result)
            Log.i("WeatherViewModel", "findCityWeatherByName: result=$result")
        }
    }

    /**
     * Gets the forecast for the next 4 days
     */
    private fun getCityNextDaysForecast(cityName: String) {
        //todo demonstrate a loading while requesting
        viewModelScope.launch {
            val result = weatherRepository.getCityNextDaysForecast(cityName)
            _forecastWeatherFlow.emit(result)
            Log.i("WeatherViewModel", "getCityNextDaysForecast: result=$result")
        }
    }


}
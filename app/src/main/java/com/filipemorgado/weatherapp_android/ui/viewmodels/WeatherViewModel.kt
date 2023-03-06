package com.filipemorgado.weatherapp_android.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.filipemorgado.weatherapp_android.data.model.response.RealtimeForecastDataResponse
import com.filipemorgado.weatherapp_android.data.repositories.WeatherRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class WeatherViewModel(private val weatherRepository: WeatherRepository) : ViewModel() {

    // Flow that keeps up to date the state of the current weather request and its result, success or error.
    private val _tickFlow = MutableSharedFlow<Result<RealtimeForecastDataResponse>>()
    val tickFlow = _tickFlow.asSharedFlow()


    init {
        // Request data from OpenWeather API
        //todo make it the saved city data to be requested
        findCityWeatherByName("Coimbra")
        fourDaysForecast("Coimbra")
    }

    /**
     * Gets the current weather by city name
     */
    private fun findCityWeatherByName(cityName: String) {
        //todo demonstrate a loading while requesting
        viewModelScope.launch {
            val result = weatherRepository.findCityWeatherByName(cityName)
            _tickFlow.emit(result)
            Log.i("WeatherViewModel", "findCityWeatherByName: result=$result")
        }
    }

    /**
     * Gets the forecast for the next 4 days
     */
    private fun fourDaysForecast(cityName: String) {
        //todo demonstrate a loading while requesting
        viewModelScope.launch {
            val result = weatherRepository.fourDaysForecast(cityName)
            Log.i("WeatherViewModel", "fourDaysForecast: result=$result")
        }
    }


}
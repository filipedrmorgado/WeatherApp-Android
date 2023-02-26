package com.filipemorgado.weatherapp_android.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.filipemorgado.weatherapp_android.data.repositories.WeatherRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class WeatherViewModel(private val weatherRepository: WeatherRepository) : ViewModel() {

    // Backing property to avoid flow emissions from other classes
    private val _tickFlow = MutableSharedFlow<Unit>()
    val tickFlow = _tickFlow.asSharedFlow()

    init {
        // Request data from OpenWeather API
        findCityWeatherByName("Coimbra")
    }

    /**
     * Initial request
     */
    private fun findCityWeatherByName(cityName: String) {
        //todo demonstrate a loading while requesting
        viewModelScope.launch {
            val result = weatherRepository.findCityWeatherByName(cityName)
        }

    }


}
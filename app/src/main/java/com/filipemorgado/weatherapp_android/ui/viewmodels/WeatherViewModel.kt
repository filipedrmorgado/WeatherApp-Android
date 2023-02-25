package com.filipemorgado.weatherapp_android.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.filipemorgado.weatherapp_android.data.repositories.WeatherRepository

class WeatherViewModel(private val weatherRepository: WeatherRepository) : ViewModel() {


    init {
        // Request data from OpenWeather API
    }

    // TODO: Implement the ViewModel
}
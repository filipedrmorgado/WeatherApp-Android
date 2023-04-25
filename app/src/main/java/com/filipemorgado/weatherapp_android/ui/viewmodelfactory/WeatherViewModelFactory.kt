package com.filipemorgado.weatherapp_android.ui.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.filipemorgado.weatherapp_android.data.repositories.CitiesDataRepository
import com.filipemorgado.weatherapp_android.data.repositories.WeatherRepository
import com.filipemorgado.weatherapp_android.ui.viewmodels.WeatherViewModel


@Suppress("UNCHECKED_CAST")
class WeatherViewModelFactory(
    private val weatherRepository: WeatherRepository,
    private val citiesRepository: CitiesDataRepository,
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return WeatherViewModel(weatherRepository,citiesRepository) as T
    }
}



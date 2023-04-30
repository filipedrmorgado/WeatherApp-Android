package com.filipemorgado.weatherapp_android.ui.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.filipemorgado.weatherapp_android.data.model.response.GeoDBCitiesResponse
import com.filipemorgado.weatherapp_android.data.model.response.NextDaysForecastResponse
import com.filipemorgado.weatherapp_android.data.model.response.RealtimeForecastDataResponse
import com.filipemorgado.weatherapp_android.data.repositories.CitiesDataRepository
import com.filipemorgado.weatherapp_android.data.repositories.WeatherRepository
import com.filipemorgado.weatherapp_android.utils.DEFAULT_CITY
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

    // Cities related data
    private val _cityList = MutableSharedFlow<GeoDBCitiesResponse>()
    val cityList = _cityList.asSharedFlow()

    private lateinit var _requestedList: MutableList<String>
    val requestedList: MutableList<String>
        get() = _requestedList

    // Requested city data
    private var _currentCityToBeDisplayed = MutableLiveData<String>()
    val currentCityToBeDisplayed: LiveData<String> = _currentCityToBeDisplayed

    init {
        // Request data from OpenWeather API
        //todo make it the saved city data to be requested
        viewModelScope.launch(Dispatchers.IO) {
            //todo remove later
            val currentWeatherDeferred = async { findCityWeatherByName(DEFAULT_CITY) }
            val forecastDeferred = async { getCityNextDaysForecast(DEFAULT_CITY) }

            //  Ensures that the shared flows _currentWeatherFlow and _forecastWeatherFlow are both updated
            //  with the latest data before the UI can start observing them.
            currentWeatherDeferred.await()
            forecastDeferred.await()
        }
    }

    /**
     * Gets the current weather by city name
     */
    suspend fun findCityWeatherByName(cityName: String) = withContext(Dispatchers.IO) {
        // Updates the city
        //todo demonstrate a loading while requesting
        val result = weatherRepository.findCityWeatherByName(cityName)
        if(result.isFailure) {
            Log.e("WeatherViewModel", "findCityWeatherByName: Error retrieving data.")
            return@withContext
        }
        val responseData = result.getOrNull()
        if(responseData == null) {
            Log.e("WeatherViewModel", "findCityWeatherByName: responseData is null.")
            return@withContext
        }
        Log.e("WeatherViewModel", "ZZZZ findCityWeatherByName: responseData=$responseData")
        // Trigger observers
        withContext(Dispatchers.Main) {
            _currentCityToBeDisplayed.value = cityName
        }
        _currentWeatherFlow.emit(responseData)
        _currentWeather = result.getOrNull()
    }

    /**
     * Gets the forecast for the next 4 days
     */
    suspend fun getCityNextDaysForecast(cityName: String) = withContext(Dispatchers.IO) {
        //todo demonstrate a loading while requesting
        val result = weatherRepository.getCityNextDaysForecast(cityName)

        if(result.isFailure) {
            Log.e("WeatherViewModel", "currentWeatherUpdate: Error retrieving data.")
            return@withContext
        }
        val responseData = result.getOrNull()
        if(responseData == null) {
            Log.e("WeatherViewModel", "currentWeatherUpdate: responseData is null.")
            return@withContext
        }

        // Trigger observers
        _forecastWeatherFlow.emit(responseData)
        _forecastWeather = result.getOrNull()
    }


    suspend fun findCitiesStartingWith(cityPrefix: String) = withContext(Dispatchers.IO) {
        //todo demonstrate a loading while requesting
        val result = citiesDataRepository.findCitiesStartingWith(cityPrefix)
        if(result.isFailure) {
            Log.e("WeatherViewModel", "getCityName: Error retrieving data.")
            return@withContext
        }
        val responseData = result.getOrNull()
        if(responseData == null) {
            Log.e("WeatherViewModel", "getCityName: responseData is null.")
            return@withContext
        }
        // Forms a list of the first 5 unique cities obtained by the request to the API.
        _requestedList = responseData.data.map { it.city }.distinct().take(5).toMutableList()

        // Trigger observers
        _cityList.emit(responseData)
    }

}
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
import com.filipemorgado.weatherapp_android.data.sharedpreferences.SharedPreferencesHelper
import com.filipemorgado.weatherapp_android.utils.DEFAULT_CITY
import com.filipemorgado.weatherapp_android.utils.SHARED_PREF_LATEST_CURRENT_WEATHER
import com.filipemorgado.weatherapp_android.utils.SHARED_PREF_LATEST_FORECAST_WEATHER
import com.filipemorgado.weatherapp_android.utils.SHARED_PREF_LATEST_REQUESTED_CITY
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WeatherViewModel(
    private val weatherRepository: WeatherRepository,
    private val citiesDataRepository: CitiesDataRepository,
    private val sharedPreferencesHelper: SharedPreferencesHelper,
    ) : ViewModel() {

    // Flow that keeps up to date the state of the current weather data.
    private var _currentWeather = MutableLiveData<RealtimeForecastDataResponse>()
    val currentWeather: LiveData<RealtimeForecastDataResponse> = _currentWeather

    // Flow that keeps up to date the state of the forecast requested weather data.
    private var _forecastWeather = MutableLiveData<NextDaysForecastResponse>()
    val forecastWeather: LiveData<NextDaysForecastResponse> = _forecastWeather

    // Cities related data
    private val _cityList = MutableSharedFlow<GeoDBCitiesResponse>()
    val cityList = _cityList.asSharedFlow()

    private lateinit var _requestedList: MutableList<String>
    val requestedList: MutableList<String>
        get() = _requestedList

    // Requested city data
    private var _currentCityToBeDisplayed = MutableLiveData<String>()
    val currentCityToBeDisplayed: LiveData<String> = _currentCityToBeDisplayed

    // Used to display errors to be displayed to the user
    private var _errorOccurredEvent = MutableLiveData<String>()
    val errorOccurredEvent: LiveData<String> = _errorOccurredEvent

    init {
        // Updates with the latest saved requested data.
        setInitialData()
    }

    /**
     * Sets the user initial data to be displayed when opening the app
     */
    private fun setInitialData() {
        val latestCurrentWeatherForecast = sharedPreferencesHelper.getObject(
            SHARED_PREF_LATEST_CURRENT_WEATHER, RealtimeForecastDataResponse::class.java)
        val latestNextDaysForecast = sharedPreferencesHelper.getObject(
            SHARED_PREF_LATEST_FORECAST_WEATHER, NextDaysForecastResponse::class.java)
        val latestRequestedCity = sharedPreferencesHelper.getObject(
            SHARED_PREF_LATEST_REQUESTED_CITY, String::class.java)

        // We should have all the data or none.
        if(latestCurrentWeatherForecast != null && latestNextDaysForecast != null && latestRequestedCity != null) {
            _currentWeather.value = latestCurrentWeatherForecast as RealtimeForecastDataResponse
            _forecastWeather.value = latestNextDaysForecast as NextDaysForecastResponse
            _currentCityToBeDisplayed.value = latestRequestedCity as String
        }else{
            makeDefaultDataRequest()
        }
    }

    /**
     * Makes a default data request based on user location if no previous data exists
     */
    private fun makeDefaultDataRequest() {
        //todo Default request based on location
        viewModelScope.launch(Dispatchers.IO) {
            val currentWeatherDeferred = viewModelScope.async { findCityWeatherByName(DEFAULT_CITY) }
            val forecastDeferred = viewModelScope.async { getCityNextDaysForecast(DEFAULT_CITY) }
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
        val responseData = result.getOrNull()
        if(result.isFailure || responseData == null) {
            _errorOccurredEvent.postValue("Error occurred while retrieving $cityName weather.")
            fallBackRequest()
            Log.e("WeatherViewModel", "findCityWeatherByName: Error retrieving data.")
            return@withContext
        }
        // Trigger observers
        withContext(Dispatchers.Main) {
            _currentCityToBeDisplayed.value = cityName
            _currentWeather.value = result.getOrNull()
        }
    }

    /**
     * Gets the forecast for the next 4 days
     */
    suspend fun getCityNextDaysForecast(cityName: String) = withContext(Dispatchers.IO) {
        //todo demonstrate a loading while requesting
        val result = weatherRepository.getCityNextDaysForecast(cityName)
        val responseData = result.getOrNull()
        if(result.isFailure || responseData == null) {
            Log.e("WeatherViewModel", "currentWeatherUpdate: Error retrieving data.")
            fallBackRequest()
            return@withContext
        }
        // Trigger observers
        withContext(Dispatchers.Main) {
            _forecastWeather.value = result.getOrNull()
        }
    }


    suspend fun findCitiesStartingWith(cityPrefix: String) = withContext(Dispatchers.IO) {
        //todo demonstrate a loading while requesting
        val result = citiesDataRepository.findCitiesStartingWith(cityPrefix)
        val responseData = result.getOrNull()
        if(result.isFailure || responseData == null) {
            Log.e("WeatherViewModel", "findCitiesStartingWith: Error retrieving data.")
            return@withContext
        }
        // Forms a list of the first 5 unique cities obtained by the request to the API.
        _requestedList = responseData.data.map { it.city }.distinct().take(5).toMutableList()
        // Trigger observers
        _cityList.emit(responseData)
    }

    /**
     * Fallback request to a city that exists, in order to have fallback data
     */
    private suspend fun fallBackRequest() {
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
}
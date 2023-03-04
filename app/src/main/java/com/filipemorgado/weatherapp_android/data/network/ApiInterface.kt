package com.filipemorgado.weatherapp_android.data.network

import com.filipemorgado.weatherapp_android.data.model.response.RealtimeForecastDataResponse
import com.filipemorgado.weatherapp_android.data.model.response.WeatherDataResponse
import com.filipemorgado.weatherapp_android.utils.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface ApiInterface {
//https://api.openweathermap.org/data/2.5/weather?q=Coimbra&units=metric&appid=$2b143e77fac5930fec440f577ebb21b7


    @GET("current.json?")
    suspend fun findCityWeatherData(
        @Query("key") appid: String = WEATHERAPI_KEY,
        @Query("q") cityName: String,
    ): Response<RealtimeForecastDataResponse>

    @GET("/forecast/weather/hourly")
    suspend fun fourDaysForecast(
        @Query("q") cityName: String,
        @Query("units") units: String = WEATHER_UNIT,
        @Query("appid") appid: String = API_KEY
    ): Response<WeatherDataResponse>

    companion object {
        operator fun invoke(
            networkConnectionInterceptor: NetworkConnectionInterceptor
        ): ApiInterface {

            val okkHttpclient = OkHttpClient.Builder()
                .addInterceptor(networkConnectionInterceptor)
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build()

            return Retrofit.Builder()
                .client(okkHttpclient)
                .baseUrl(WEATHERAPI_SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiInterface::class.java)
        }
    }
}
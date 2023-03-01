package com.filipemorgado.weatherapp_android.data.network

import com.filipemorgado.weatherapp_android.data.model.response.WeatherDataResponse
import com.filipemorgado.weatherapp_android.utils.API_KEY
import com.filipemorgado.weatherapp_android.utils.SERVER_URL
import com.filipemorgado.weatherapp_android.utils.WEATHER_UNIT
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface ApiInterface {

    @GET("weather")
    suspend fun findCityWeatherData(
        @Query("q") cityName: String,
        @Query("units") units: String = WEATHER_UNIT,
        @Query("appid") appid: String = API_KEY
    ): Response<WeatherDataResponse>

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
                .baseUrl(SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiInterface::class.java)
        }
    }
}
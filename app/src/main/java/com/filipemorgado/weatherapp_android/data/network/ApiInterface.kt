package com.filipemorgado.weatherapp_android.data.network

import com.filipemorgado.weatherapp_android.data.model.response.NextDaysForecastResponse
import com.filipemorgado.weatherapp_android.data.model.response.RealtimeForecastDataResponse
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

    @GET("current.json?")
    suspend fun findCityWeatherData(
        @Query("key") appid: String = WEATHERAPI_KEY,
        @Query("q") cityName: String,
    ): Response<RealtimeForecastDataResponse>

    @GET("forecast.json?")
    suspend fun getCityNextDaysForecast(
        @Query("key") appid: String = WEATHERAPI_KEY,
        @Query("q") cityName: String,
        @Query("days") days: Int = FORECAST_DAYS,
    ): Response<NextDaysForecastResponse>

    companion object {
        operator fun invoke(
            networkConnectionInterceptor: NetworkConnectionInterceptor
        ): ApiInterface {

            val okkHttpclient = OkHttpClient.Builder()
                .addInterceptor(networkConnectionInterceptor)
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .connectTimeout(1, TimeUnit.MINUTES) //todo add constants for timeouts.
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
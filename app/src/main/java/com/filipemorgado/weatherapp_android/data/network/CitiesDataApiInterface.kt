package com.filipemorgado.weatherapp_android.data.network

import com.filipemorgado.weatherapp_android.data.model.response.GeoNamesResponse
import com.filipemorgado.weatherapp_android.utils.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface CitiesDataApiInterface {

    @GET("searchJSON")
    fun searchCities(
        @Query("name") name: String,
        @Query("maxRows") maxRows: Int = 10,
        @Query("username") username: String = "YOUR_USERNAME_HERE"
    ): Response<GeoNamesResponse>

    companion object {
        operator fun invoke(
            networkConnectionInterceptor: NetworkConnectionInterceptor
        ): CitiesDataApiInterface {

            val okkHttpclient = OkHttpClient.Builder()
                .addInterceptor(networkConnectionInterceptor)
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.MINUTES) //todo add constants for timeouts.
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                .build()

            return Retrofit.Builder()
                .client(okkHttpclient)
                .baseUrl(WEATHER_API_SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(CitiesDataApiInterface::class.java)
        }
    }
}
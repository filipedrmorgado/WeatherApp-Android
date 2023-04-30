package com.filipemorgado.weatherapp_android.data.network

import com.filipemorgado.weatherapp_android.data.model.response.GeoDBCitiesResponse
import com.filipemorgado.weatherapp_android.utils.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface CitiesDataApiInterface {

    @GET("v1/geo/cities")
    suspend fun getCities(
        @Query("limit") limit: Int = 10,
        @Query("offset") offset: Int = 0,
        @Query("namePrefix") namePrefix: String,
        @Header("X-RapidAPI-Key") apiKey: String = GEO_CITIES_API_KEY
    ): Response<GeoDBCitiesResponse>

    companion object {
        operator fun invoke(
            networkConnectionInterceptor: NetworkConnectionInterceptor
        ): CitiesDataApiInterface {

            val okkHttpclient = OkHttpClient.Builder()
                .addInterceptor(networkConnectionInterceptor)
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.MINUTES)
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                .build()

            return Retrofit.Builder()
                .client(okkHttpclient)
                .baseUrl(GEO_CITIES_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(CitiesDataApiInterface::class.java)
        }
    }
}
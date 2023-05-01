package com.filipemorgado.weatherapp_android

import android.app.Application
import com.filipemorgado.weatherapp_android.data.network.CitiesDataApiInterface
import com.filipemorgado.weatherapp_android.data.network.NetworkConnectionInterceptor
import com.filipemorgado.weatherapp_android.data.network.WeatherDataApiInterface
import com.filipemorgado.weatherapp_android.data.repositories.CitiesDataRepository
import com.filipemorgado.weatherapp_android.data.repositories.WeatherRepository
import com.filipemorgado.weatherapp_android.data.sharedpreferences.SharedPreferencesHelper
import com.filipemorgado.weatherapp_android.ui.viewmodelfactory.WeatherViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class WeatherApplication : Application(), KodeinAware {

    override val kodein = Kodein.lazy {
        import(androidXModule(this@WeatherApplication))

        bind() from singleton { NetworkConnectionInterceptor(instance()) }
        bind() from singleton { WeatherDataApiInterface(instance()) }
        bind() from singleton { CitiesDataApiInterface(instance()) }
        // Repositories
        bind() from singleton { WeatherRepository(instance(), instance()) }
        bind() from singleton { CitiesDataRepository(instance() /*instance()*/) }

        // Shared Preferences
        bind() from singleton { SharedPreferencesHelper(instance()) }

        bind() from provider { WeatherViewModelFactory(instance(), instance(), instance()) }
        //bind() from provider { WeatherDatabase(instance()) }
    }
}
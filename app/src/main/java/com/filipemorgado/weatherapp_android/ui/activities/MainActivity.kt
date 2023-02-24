package com.filipemorgado.weatherapp_android.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.filipemorgado.weatherapp_android.R
import com.filipemorgado.weatherapp_android.ui.fragments.MainFragment
import com.filipemorgado.weatherapp_android.ui.viewmodelfactory.WeatherViewModelFactory
import com.filipemorgado.weatherapp_android.ui.viewmodels.WeatherViewModel
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class MainActivity : AppCompatActivity(), KodeinAware {

    override val kodein by kodein()

    private val factory: WeatherViewModelFactory by instance()
    private lateinit var weatherViewModel: WeatherViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize viewmodel
        weatherViewModel = ViewModelProvider(this, factory)[WeatherViewModel::class.java]

        // Run gitignore
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment())
                .commitNow()
        }
    }
}
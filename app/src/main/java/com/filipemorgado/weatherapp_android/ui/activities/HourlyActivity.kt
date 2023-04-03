package com.filipemorgado.weatherapp_android.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.filipemorgado.weatherapp_android.databinding.ActivityHourlyBinding
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein

class HourlyActivity : AppCompatActivity(), KodeinAware {
    override val kodein by kodein()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityHourlyBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}
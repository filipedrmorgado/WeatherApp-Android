package com.filipemorgado.weatherapp_android.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.filipemorgado.weatherapp_android.data.model.response.RealtimeForecastDataResponse
import com.filipemorgado.weatherapp_android.databinding.ActivityHourlyBinding
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein

class HourlyActivity : AppCompatActivity(), KodeinAware {
    override val kodein by kodein()

    private lateinit var extra: RealtimeForecastDataResponse

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityHourlyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //todo get data from bundle and set the data
        binding.dayNameTextView.text = "Testing Data"
        binding.maxTempTextView.text = "25º"
        binding.minTempTextView.text = "-24º"
        binding.tempTextView.text = "15º"

    }

}
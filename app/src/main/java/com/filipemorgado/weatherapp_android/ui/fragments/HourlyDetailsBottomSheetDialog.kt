package com.filipemorgado.weatherapp_android.ui.fragments

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.filipemorgado.weatherapp_android.data.model.response.ForecastDayData
import com.filipemorgado.weatherapp_android.databinding.FragmentHourlyDetailsBottomDialogBinding
import com.filipemorgado.weatherapp_android.ui.viewmodels.WeatherViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class HourlyDetailsBottomSheetDialog : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentHourlyDetailsBottomDialogBinding
    private val weatherViewModel: WeatherViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHourlyDetailsBottomDialogBinding.inflate(inflater)

        //todo prevent force checks maybe "as"
        val forecastDayData = arguments?.getSerializable("my_data") as ForecastDayData


        //todo get data from bundle and set the data
        binding.dayNameTextView.text = forecastDayData.day.condition.text
        binding.maxTempTextView.text = forecastDayData.day.mintemp_c.toString()
        binding.minTempTextView.text = forecastDayData.day.maxtemp_c.toString()
        binding.tempTextView.text = forecastDayData.day.avgtemp_c.toString()
        //todo fix the color based on where clicked
        binding.bottomSheetDialog.setBackgroundColor(Color.RED)
        //binding.cardView.setBackgroundColor(Color.RED)


        Log.i("MainFragment", "setupObservers: ZZZ V6 forecastDayData=$forecastDayData")
        return binding.root
    }

}
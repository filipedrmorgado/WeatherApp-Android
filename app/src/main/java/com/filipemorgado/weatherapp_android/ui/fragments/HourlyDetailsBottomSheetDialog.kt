package com.filipemorgado.weatherapp_android.ui.fragments

import android.content.res.ColorStateList
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import com.filipemorgado.weatherapp_android.data.model.response.ForecastDayData
import com.filipemorgado.weatherapp_android.databinding.FragmentHourlyDetailsBottomDialogBinding
import com.filipemorgado.weatherapp_android.ui.viewmodels.WeatherViewModel
import com.filipemorgado.weatherapp_android.utils.AppUtils
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.filipemorgado.weatherapp_android.R



class HourlyDetailsBottomSheetDialog : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentHourlyDetailsBottomDialogBinding
    private val weatherViewModel: WeatherViewModel by activityViewModels()

    private lateinit var forecastDayData: ForecastDayData
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHourlyDetailsBottomDialogBinding.inflate(inflater)
        //todo prevent force checks maybe "as"
        forecastDayData = arguments?.getSerializable("my_data") as ForecastDayData
        setBottomSheetData()
        return binding.root
    }

    private fun setBottomSheetData() {
        //todo get data from bundle and set the data
        binding.dayNameTextView.text = forecastDayData.day.condition.text
        binding.maxTempTextView.text = forecastDayData.day.mintemp_c.toString()
        binding.minTempTextView.text = forecastDayData.day.maxtemp_c.toString()
        binding.tempTextView.text = forecastDayData.day.avgtemp_c.toString()
        binding.bottomSheetAnimationView.setAnimation(AppUtils.getWeatherAnimation(forecastDayData.day.condition.code))
        binding.bottomSheetAnimationView.playAnimation()

        //todo fix the color based on where clicked
        val myColor = ContextCompat.getColor(requireContext(), R.color.material_blue)

        val colorStateList = ColorStateList.valueOf(myColor)
        binding.bottomSheetDialog.backgroundTintList = colorStateList
        binding.bottomSheetDialog.backgroundTintMode = PorterDuff.Mode.SRC_IN;
    }

}
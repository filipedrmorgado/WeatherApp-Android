package com.filipemorgado.weatherapp_android.ui.fragments

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
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


        //todo get data from bundle and set the data
        binding.dayNameTextView.text = "Testing Data"
        binding.maxTempTextView.text = "25º"
        binding.minTempTextView.text = "-24º"
        binding.tempTextView.text = "15º"
        binding.bottomSheetDialog.setBackgroundColor(Color.RED)
        //binding.cardView.setBackgroundColor(Color.RED)


        Log.i("MainFragment", "setupObservers: ZZZ V6 ")
        return binding.root
    }

}
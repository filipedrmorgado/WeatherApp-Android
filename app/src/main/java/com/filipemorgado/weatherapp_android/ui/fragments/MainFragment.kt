package com.filipemorgado.weatherapp_android.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.filipemorgado.weatherapp_android.databinding.FragmentMainBinding
import com.filipemorgado.weatherapp_android.ui.viewmodels.WeatherViewModel

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private lateinit var viewModel: WeatherViewModel

    //private lateinit var
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[WeatherViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }


}
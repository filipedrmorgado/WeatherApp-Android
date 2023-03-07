package com.filipemorgado.weatherapp_android.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.filipemorgado.weatherapp_android.R
import com.filipemorgado.weatherapp_android.data.model.response.RealtimeForecastDataResponse
import com.filipemorgado.weatherapp_android.databinding.FragmentMainBinding
import com.filipemorgado.weatherapp_android.ui.adapters.MultipleDaysRecyclerView
import com.filipemorgado.weatherapp_android.ui.viewmodels.WeatherViewModel
import com.filipemorgado.weatherapp_android.utils.AppUtils
import java.util.*

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private val weatherViewModel: WeatherViewModel by activityViewModels()

    private lateinit var multipleDaysRecyclerView: MultipleDaysRecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)

        // Setting up the fragment
        setupUI()
        setupObservers()
        return binding.root
    }

    /**
     * Setup initial UI
     */
    private fun setupUI() {
        initializeRecyclerView()
        setupTextSwitchers()
        //todo make it dynamic
        binding.toolbarLayout.cityNameTextView.text = "Coimbra, PT"
    }

    /**
     * Setup text Switchers
     */
    private fun setupTextSwitchers() {
        binding.contentMainLayout.tempTextView.setFactory {
            TextView(
                ContextThemeWrapper(
                    context,
                    R.style.TempTextView
                ), null, 0
            )
        }
        binding.contentMainLayout.tempTextView.setInAnimation(
            requireContext(),
            R.anim.slide_in_right
        )
        binding.contentMainLayout.tempTextView.setOutAnimation(
            requireContext(),
            R.anim.slide_out_left
        )

        binding.contentMainLayout.descriptionTextView.setFactory {
            TextView(
                ContextThemeWrapper(
                    context,
                    R.style.DescriptionTextView
                ), null, 0
            )
        }
        binding.contentMainLayout.descriptionTextView.setInAnimation(
            requireContext(),
            R.anim.slide_in_right
        )
        binding.contentMainLayout.descriptionTextView.setOutAnimation(
            requireContext(),
            R.anim.slide_out_left
        )

        binding.contentMainLayout.humidityTextView.setFactory {
            TextView(
                ContextThemeWrapper(
                    context,
                    R.style.HumidityTextView
                ), null, 0
            )
        }
        binding.contentMainLayout.humidityTextView.setInAnimation(
            requireContext(),
            R.anim.slide_in_bottom
        )
        binding.contentMainLayout.humidityTextView.setOutAnimation(
            requireContext(),
            R.anim.slide_out_top
        )

        binding.contentMainLayout.windTextView.setFactory {
            TextView(
                ContextThemeWrapper(
                    context,
                    R.style.WindSpeedTextView
                ), null, 0
            )
        }
        binding.contentMainLayout.windTextView.setInAnimation(
            requireContext(),
            R.anim.slide_in_bottom
        )
        binding.contentMainLayout.windTextView.setOutAnimation(
            requireContext(),
            R.anim.slide_out_top
        )
    }

    /**
     * Initialize Recycler View
     */
    private fun initializeRecyclerView() {
        multipleDaysRecyclerView = MultipleDaysRecyclerView()
        val mLayoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false
        )
        binding.contentMainLayout.recyclerView.apply {
            layoutManager = mLayoutManager
            itemAnimator = DefaultItemAnimator()
            adapter = multipleDaysRecyclerView
        }
    }

    /**
     * Observe changes in order to update the screen data
     */
    private fun setupObservers() {
        lifecycleScope.launchWhenCreated {
            weatherViewModel.currentWeatherFlow.collect {
                dateUpdateReceived(it)
                Log.i("MainFragment", "setupObservers: Received Current Weather Data to Update.")
            }
        }

        lifecycleScope.launchWhenCreated {
            weatherViewModel.forecastWeatherFlow.collect {
                //todo update recycler
                Log.i("MainFragment", "setupObservers: Received Forecast Weather Data to Update.")
            }
        }
    }

    private fun dateUpdateReceived(result: Result<RealtimeForecastDataResponse>) {
        when {
            // Update screen data with new info
            result.isSuccess -> {
                val responseData = result.getOrThrow()
                Log.i("MainFragment", "dateUpdateReceived: Data updated on the screen")
                with(binding.contentMainLayout) {
                    tempTextView.setText(
                        String.format(
                            Locale.getDefault(),
                            "%.0f°",
                            responseData.current.tempC
                        )
                    )
                    descriptionTextView.setText(
                        String.format(
                            Locale.getDefault(),
                            "%s",
                            responseData.current.condition.text
                        )
                    )
                    humidityTextView.setText(
                        String.format(
                            Locale.getDefault(),
                            "%s%%",
                            responseData.current.humidity
                        )
                    )
                    windTextView.setText(
                        String.format(
                            Locale.getDefault(),
                            "%s km/hr",
                            responseData.current.windKph
                        )
                    )
                    //todo update with more accuracy regarding images, according to API details of the weather
                    animationView.setAnimation(AppUtils.getWeatherAnimation(responseData.current.condition.code))
                    animationView.playAnimation()
                }
            }
            // Error occurred retrieving data
            result.isFailure -> {
                Log.e("MainFragment", "dateUpdateReceived: Error retrieving data.")
            }
        }
    }

}
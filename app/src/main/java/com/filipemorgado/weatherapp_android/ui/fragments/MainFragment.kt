package com.filipemorgado.weatherapp_android.ui.fragments

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.filipemorgado.weatherapp_android.R
import com.filipemorgado.weatherapp_android.data.model.CurrentWeatherDetails
import com.filipemorgado.weatherapp_android.data.model.response.ForecastDayData
import com.filipemorgado.weatherapp_android.data.model.response.NextDaysForecastResponse
import com.filipemorgado.weatherapp_android.databinding.FragmentMainBinding
import com.filipemorgado.weatherapp_android.ui.adapters.MultipleDaysRecyclerView
import com.filipemorgado.weatherapp_android.ui.viewmodels.WeatherViewModel
import com.filipemorgado.weatherapp_android.utils.AppUtils
import com.filipemorgado.weatherapp_android.utils.RECYCLER_SIZE
import kotlinx.coroutines.launch

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private val weatherViewModel: WeatherViewModel by activityViewModels()

    private lateinit var multipleDaysRecyclerView: MultipleDaysRecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        // Setting up the UI and Observers
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
        setupInitialButtonSelectorUI()
    }

    private fun setupInitialButtonSelectorUI() {
        val color = ContextCompat.getColor(requireContext(),R.color.button_background_grey)
        val btnTextColor = ContextCompat.getColor(requireContext(),R.color.material_blue)
        binding.contentMainLayout.currentWeatherSelector.todaySelector.backgroundTintList = ColorStateList.valueOf(color)
        binding.contentMainLayout.currentWeatherSelector.todaySelector.setTextColor(btnTextColor)
        binding.contentMainLayout.currentWeatherSelector.tomorrowSelector.backgroundTintList = ColorStateList.valueOf(Color.TRANSPARENT)
        binding.contentMainLayout.currentWeatherSelector.nextWeekSelector.backgroundTintList = ColorStateList.valueOf(Color.TRANSPARENT)
    }

    /**
     * Setup text Switchers
     */
    private fun setupTextSwitchers() {
        binding.contentMainLayout.tempTextView.setFactory { TextView(ContextThemeWrapper(context,R.style.TempTextView), null, 0) }
        binding.contentMainLayout.tempTextView.setInAnimation(context,R.anim.slide_in_right)
        binding.contentMainLayout.tempTextView.setOutAnimation(context,R.anim.slide_out_left)

        binding.contentMainLayout.descriptionTextView.setFactory { TextView(ContextThemeWrapper(context, R.style.DescriptionTextView), null, 0) }
        binding.contentMainLayout.descriptionTextView.setInAnimation(context, R.anim.slide_in_right)
        binding.contentMainLayout.descriptionTextView.setOutAnimation(context, R.anim.slide_out_left)

        binding.contentMainLayout.humidityTextView.setFactory { TextView(ContextThemeWrapper(context, R.style.HumidityTextView), null, 0) }
        binding.contentMainLayout.humidityTextView.setInAnimation(context, R.anim.slide_in_bottom)
        binding.contentMainLayout.humidityTextView.setOutAnimation(context, R.anim.slide_out_top)

        binding.contentMainLayout.windTextView.setFactory { TextView(ContextThemeWrapper(context, R.style.WindSpeedTextView), null, 0) }
        binding.contentMainLayout.windTextView.setInAnimation(context, R.anim.slide_in_bottom)
        binding.contentMainLayout.windTextView.setOutAnimation(context, R.anim.slide_out_top)
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

        // Setup on click observers to launch details
        multipleDaysRecyclerView.setOnItemClickListener(object : MultipleDaysRecyclerView.OnDayDetailsClickListener {
            override fun onDayClick(position: Int) {
                val dataToBeSent = weatherViewModel.forecastWeather?.forecast?.forecastday?.get(position)
                val colorToBeSet = getColorBasedOnIndex(position)
                launchBottomSheetDialog(dataToBeSent, colorToBeSet)
            }
        })
    }

    /**
     * Gets the color for the bottom sheet based on index, so it can maintain the same color as the
     * recycler view in the main view.
     */
    private fun getColorBasedOnIndex(position: Int): Int {
        val colorInfo = AppUtils.RECYCLER_COLORS[position % RECYCLER_SIZE]
        return ContextCompat.getColor(binding.root.context, colorInfo)
    }

    /**
     * Observe changes in order to update the screen data
     */
    private fun setupObservers() {
        lifecycleScope.launch {
            weatherViewModel.currentWeatherFlow.collect {
                val detailsDataClass = CurrentWeatherDetails(it.current.tempC, it.current.humidity, it.current.windKph,it.current.condition.text, it.current.condition.code)
                currentWeatherUpdate(detailsDataClass)
                Log.i("MainFragment", "setupObservers: Received Current Weather Data to Update.")
            }
        }

        lifecycleScope.launch {
            weatherViewModel.forecastWeatherFlow.collect {
                forecastWeatherUpdate(it)
                Log.i("MainFragment", "setupObservers: Received Forecast Weather Data to Update.")
            }
        }

        binding.contentMainLayout.todayMaterialCard.setOnClickListener {
            lifecycleScope.launch {
                val dataToBeSent = weatherViewModel.forecastWeather?.forecast?.forecastday?.get(0)
                val colorToBeSet = ContextCompat.getColor(requireContext(),R.color.material_blue)
                launchBottomSheetDialog(dataToBeSent, colorToBeSet)
            }
        }

        binding.contentMainLayout.currentWeatherSelector.todaySelector.setOnClickListener {
            onTodaySelectorClick()
        }
        binding.contentMainLayout.currentWeatherSelector.tomorrowSelector.setOnClickListener {
            onTomorrowSelectorClick()
        }
        binding.contentMainLayout.currentWeatherSelector.nextWeekSelector.setOnClickListener {
            onNextWeekSelectorClick()
        }
    }

    private fun onTodaySelectorClick() {
        val currentData = weatherViewModel.currentWeather?.current ?: return
        val detailsDataClass = CurrentWeatherDetails(currentData.tempC, currentData.humidity, currentData.windKph,currentData.condition.text, currentData.condition.code)
        currentWeatherUpdate(detailsDataClass)
        // Updates UI
        with(binding.contentMainLayout.currentWeatherSelector) {
            todaySelector.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(),R.color.button_background_grey))
            todaySelector.setTextColor(ContextCompat.getColor(requireContext(),R.color.material_blue))
            tomorrowSelector.backgroundTintList = ColorStateList.valueOf(Color.TRANSPARENT)
            tomorrowSelector.setTextColor(ContextCompat.getColor(requireContext(),R.color.colorPrimaryDarkNight))
            nextWeekSelector.backgroundTintList = ColorStateList.valueOf(Color.TRANSPARENT)
        }
    }

    private fun onTomorrowSelectorClick() {
        val tomorrowData = weatherViewModel.forecastWeather?.forecast?.forecastday?.get(1)?.day ?: return
        val detailsDataClass = CurrentWeatherDetails(tomorrowData.avgtemp_c, tomorrowData.avghumidity.toInt(), tomorrowData.maxwind_kph,tomorrowData.condition.text, tomorrowData.condition.code)
        currentWeatherUpdate(detailsDataClass)
        // Updates UI
        with(binding.contentMainLayout.currentWeatherSelector) {
            todaySelector.backgroundTintList = ColorStateList.valueOf(Color.TRANSPARENT)
            todaySelector.setTextColor( ContextCompat.getColor(requireContext(),R.color.colorPrimaryDarkNight))
            tomorrowSelector.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(),R.color.button_background_grey))
            tomorrowSelector.setTextColor(ContextCompat.getColor(requireContext(),R.color.material_blue))
            nextWeekSelector.backgroundTintList = ColorStateList.valueOf(Color.TRANSPARENT)
        }

    }

    private fun onNextWeekSelectorClick() {
        //todo not possible to implement, show a message
    }

    /**
     * Launches the BottomSheetDialog with the necessary data
     */
    private fun launchBottomSheetDialog(dataToBeSent: ForecastDayData?, colorToBeSet: Int) {
        val bottomSheetFragment = HourlyDetailsBottomSheetDialog()
        if(dataToBeSent != null) {
            val bundle = Bundle().apply {
                putSerializable("my_data", dataToBeSent)
                putInt("color", colorToBeSet)
            }
            bottomSheetFragment.arguments = bundle
            bottomSheetFragment.show(parentFragmentManager,"CustomBottomSheet")

        }else{
            //todo see what to do
        }
    }

    private fun currentWeatherUpdate(data: CurrentWeatherDetails) {
        Log.i("MainFragment", "currentWeatherUpdate: Data updated on the screen")
        // Setup data on screen
        with(binding.contentMainLayout) {
            tempTextView.setText(String.format("%.0f°",data.temp_c))
            descriptionTextView.setText(String.format("%s",data.conditionInfo))
            humidityTextView.setText(String.format("%d%%",data.humidity))
            windTextView.setText(String.format("%s km/hr",data.windKph))

            //todo update with more accuracy regarding images, according to API details of the weather
            animationView.setAnimation(AppUtils.getWeatherAnimation(data.icon))
            animationView.playAnimation()
        }
    }

    /**
     * Updates recycler forecast data
     */
    private fun forecastWeatherUpdate(resultData: NextDaysForecastResponse) {
        multipleDaysRecyclerView.setData(resultData.forecast.forecastday)
    }
}



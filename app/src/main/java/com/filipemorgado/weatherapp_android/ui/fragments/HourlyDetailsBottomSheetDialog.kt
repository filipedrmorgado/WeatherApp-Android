package com.filipemorgado.weatherapp_android.ui.fragments

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.filipemorgado.weatherapp_android.R
import com.filipemorgado.weatherapp_android.data.model.response.ForecastDayData
import com.filipemorgado.weatherapp_android.databinding.FragmentHourlyDetailsBottomDialogBinding
import com.filipemorgado.weatherapp_android.ui.adapters.HourlyWeatherRecyclerView
import com.filipemorgado.weatherapp_android.ui.viewmodels.WeatherViewModel
import com.filipemorgado.weatherapp_android.utils.*
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.*


class HourlyDetailsBottomSheetDialog : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentHourlyDetailsBottomDialogBinding
    private val weatherViewModel: WeatherViewModel by activityViewModels()

    private lateinit var forecastDayData: ForecastDayData
    private lateinit var hourlyWeatherRecyclerView: HourlyWeatherRecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHourlyDetailsBottomDialogBinding.inflate(inflater)
        //todo prevent force checks maybe "as". Also, set the state to expanded maybe?
        setBottomSheetData()
        setLineChartData()
        initializeRecyclerView()
        setRecyclerViewData()
        setupObservers()
        // Sets up the dialog as expanded
        (dialog as BottomSheetDialog).behavior.state = BottomSheetBehavior.STATE_EXPANDED
        return binding.root
    }

    private fun setupObservers() {
        binding.exitButton.setOnClickListener {
            this.dismiss()
        }
    }

    private fun setRecyclerViewData() {
        hourlyWeatherRecyclerView.setData(forecastDayData.hour)
    }

    private fun setBottomSheetData() {
        forecastDayData = arguments?.getSerializable("my_data") as ForecastDayData
        val backgroundColor = arguments?.getInt("color") ?: ContextCompat.getColor(requireContext(), R.color.material_blue)

        setupDayText()
        binding.maxTempTextView.text = forecastDayData.day.mintemp_c.toString()
        binding.minTempTextView.text = forecastDayData.day.maxtemp_c.toString()
        binding.tempTextView.text = forecastDayData.day.avgtemp_c.toString()
        binding.bottomSheetAnimationView.setAnimation(AppUtils.getWeatherAnimation(forecastDayData.day.condition.code))
        binding.bottomSheetAnimationView.playAnimation()

        // Setting background color
        binding.bottomSheetDialog.backgroundTintList = ColorStateList.valueOf(backgroundColor)
        binding.bottomSheetDialog.backgroundTintMode = PorterDuff.Mode.SRC_IN
    }

    private fun setupDayText() {
        val calendar = Calendar.getInstance()
        // Multiply by 1000 to convert from seconds to milliseconds
        calendar.timeInMillis = forecastDayData.date_epoch * 1000L
        val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
        binding.dayNameTextView.text = AppUtils.DAYS_OF_WEEK[dayOfWeek - 1]
    }

    /**
     * Initialize Recycler View
     */
    private fun initializeRecyclerView() {
        hourlyWeatherRecyclerView = HourlyWeatherRecyclerView()
        val mLayoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false
        )
        binding.hourlyRecyclerView.apply {
            layoutManager = mLayoutManager
            itemAnimator = DefaultItemAnimator()
            adapter = hourlyWeatherRecyclerView
        }
    }

    /**
     * Sets line chart data
     */
    private fun setLineChartData() {
        // Creates the dataSet for the LineChart
        val entries = mutableListOf<Entry>().apply {
            forecastDayData.hour
                .take(8) // limit the number of entries to 8
                .mapIndexedTo(this) { index, hourData ->
                    Entry(index.toFloat(), hourData.temp_c.toFloat())
                }
        }
        val dataSet = LineDataSet(entries, "Label") // add entries to dataset

        dataSet.lineWidth = LINE_CHART_LINE_WIDTH
        dataSet.circleRadius = LINE_CHART_CIRCLE_RADIUS
        dataSet.isHighlightEnabled = false
        dataSet.setCircleColor(Color.parseColor(getString(R.string.custom_linechart_circle_color)))
        dataSet.valueTextSize = LINE_CHART_DATASET_DESCRIPTION_TEXT_SIZE
        dataSet.valueTextColor = Color.WHITE
        dataSet.mode = LineDataSet.Mode.CUBIC_BEZIER
        dataSet.valueTypeface = ResourcesCompat.getFont(requireContext(), R.font.vazir)
        dataSet.valueFormatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                return String.format(Locale.getDefault(), "%.0f", value)
            }
        }
        val lineData = LineData(dataSet)
        binding.lineChart.description.isEnabled = false
        binding.lineChart.axisLeft.setDrawLabels(false)
        binding.lineChart.axisRight.setDrawLabels(false)
        binding.lineChart.xAxis.setDrawLabels(false)
        binding.lineChart.legend.isEnabled = false // Hide the legend

        binding.lineChart.xAxis.setDrawGridLines(false)
        binding.lineChart.axisLeft.setDrawGridLines(false)
        binding.lineChart.axisRight.setDrawGridLines(false)
        binding.lineChart.axisLeft.setDrawAxisLine(false)
        binding.lineChart.axisRight.setDrawAxisLine(false)
        binding.lineChart.xAxis.setDrawAxisLine(false)
        binding.lineChart.setScaleEnabled(false)
        binding.lineChart.data = lineData
        binding.lineChart.animateY(LINE_CHART_ANIMATION_TIME)
    }
}
package com.filipemorgado.weatherapp_android.ui.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.filipemorgado.weatherapp_android.data.model.response.HourData
import com.filipemorgado.weatherapp_android.databinding.WeatherHourlyItemBinding
import com.filipemorgado.weatherapp_android.utils.AppUtils
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*

class HourlyWeatherRecyclerView : RecyclerView.Adapter<HourlyWeatherRecyclerView.ViewHolder>() {

    // Hosts every forecast day details to populate the recycler
    private val weatherDetailList = ArrayList<HourData>()
    private val calendar = Calendar.getInstance()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = WeatherHourlyItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(weatherDetailList[position])
    }

    override fun getItemCount(): Int = weatherDetailList.size

    fun setData(newWeatherDetail: List<HourData>) {
        weatherDetailList.clear()
        weatherDetailList.addAll(newWeatherDetail)
        //todo we should notify only data that actually changed. Improve performance
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: WeatherHourlyItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindItems(forecastDayDataDetails: HourData) {
            binding.apply {
                this.hourlyTempTextView.text = String.format("%.0f°",forecastDayDataDetails.temp_c)
                this.hourlyTimeTextView.text = String.format("%s:30",getHourFromTheDay(forecastDayDataDetails.time_epoch))
                // Sets card colors
                binding.hourlyCardView.setCardBackgroundColor(Color.WHITE)
                AppUtils.setWeatherIcon(binding.root.context, binding.hourlyWeatherImageView,forecastDayDataDetails.condition.code)
            }
        }

        /**
         * Gets the hour of the day from the absolute time.
         */
        private fun getHourFromTheDay(timeEpoch: Long) = LocalDateTime.ofEpochSecond(timeEpoch, 0, ZoneOffset.UTC).hour.toString()

    }
}

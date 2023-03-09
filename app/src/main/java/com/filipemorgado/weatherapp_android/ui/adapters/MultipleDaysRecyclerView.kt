package com.filipemorgado.weatherapp_android.ui.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.filipemorgado.weatherapp_android.data.model.response.ForecastDay
import com.filipemorgado.weatherapp_android.databinding.WeatherDayItemBinding
import com.filipemorgado.weatherapp_android.utils.AppUtils
import java.util.*

class MultipleDaysRecyclerView : RecyclerView.Adapter<MultipleDaysRecyclerView.ViewHolder>() {

    private val weatherDetailList = ArrayList<ForecastDay>()
    private val calendar = Calendar.getInstance()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = WeatherDayItemBinding.inflate(
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

    fun setData(newWeatherDetail: List<ForecastDay>) {
        weatherDetailList.clear()
        weatherDetailList.addAll(newWeatherDetail)
        //todo we should notify only data that actually changed. Improve performance
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: WeatherDayItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindItems(forecastDayDetails: ForecastDay) {
            binding.apply {
                this.minTempTextView.text = forecastDayDetails.day.mintempC.toString()
                this.maxTempTextView.text = forecastDayDetails.day.maxtempC.toString()

                // Setting weekday
                calendar.timeInMillis = forecastDayDetails.dateEpoch
                val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
                this.dayNameTextView.text = AppUtils.DAYS_OF_WEEK[dayOfWeek]
                this.cardView.setBackgroundColor(Color.BLUE)

                //todo update with info
            }
        }
    }
}

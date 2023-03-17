package com.filipemorgado.weatherapp_android.ui.adapters

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.graphics.ColorUtils
import androidx.recyclerview.widget.RecyclerView
import com.filipemorgado.weatherapp_android.data.model.response.ForecastDayData
import com.filipemorgado.weatherapp_android.databinding.WeatherDayItemBinding
import com.filipemorgado.weatherapp_android.utils.ALPHA_RECYCLER_SHADOW_COLOR
import com.filipemorgado.weatherapp_android.utils.AppUtils
import com.filipemorgado.weatherapp_android.utils.RECYCLER_SIZE
import java.util.*

class MultipleDaysRecyclerView : RecyclerView.Adapter<MultipleDaysRecyclerView.ViewHolder>() {

    // Hosts every forecast day details to populate the recycler
    private val weatherDetailList = ArrayList<ForecastDayData>()
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
        holder.bindItems(weatherDetailList[position], position)
    }

    override fun getItemCount(): Int = weatherDetailList.size

    fun setData(newWeatherDetail: List<ForecastDayData>) {
        weatherDetailList.clear()
        weatherDetailList.addAll(newWeatherDetail)
        //todo we should notify only data that actually changed. Improve performance
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: WeatherDayItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindItems(forecastDayDataDetails: ForecastDayData, position: Int) {
            binding.apply {
                this.minTempTextView.text = String.format("%.0f°",forecastDayDataDetails.day.mintemp_c)
                this.maxTempTextView.text = String.format("%.0f°",forecastDayDataDetails.day.maxtemp_c)
                this.tempTextView.text = String.format("%.0f°",forecastDayDataDetails.day.avgtemp_c)
                // Sets weekdays names
                settingWeekdayText(forecastDayDataDetails.date_epoch)
                // Sets card colors
                settingColors(position)
                AppUtils.setWeatherIcon(binding.root.context, binding.weatherImageView,forecastDayDataDetails.day.condition.code)
            }
        }

        /**
         * Sets each weekday name for each card
         */
        private fun settingWeekdayText(dateEpoch: Long) {
            // Multiply by 1000 to convert from seconds to milliseconds
            calendar.timeInMillis = dateEpoch * 1000L
            val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
            binding.dayNameTextView.text = AppUtils.DAYS_OF_WEEK[dayOfWeek - 1]
        }

        /**
         * Setting color for the background and color with alpha added to it to build the shadow of each card
         */
        private fun settingColors(position: Int) {
            val colorInfo = AppUtils.RECYCLER_COLORS[position % RECYCLER_SIZE]
            val color = ContextCompat.getColor(binding.root.context, colorInfo)
            val alphaColor = ColorUtils.setAlphaComponent(color, ALPHA_RECYCLER_SHADOW_COLOR)

            Log.d("MultipleDaysRecyclerView","ViewHolder, bindItems: color=$color, colorInfo=$colorInfo, index=${position % RECYCLER_SIZE}")

            binding.cardView.setCardBackgroundColor(color)
            val colors = intArrayOf(Color.TRANSPARENT, alphaColor, Color.TRANSPARENT)

            val shape = GradientDrawable(GradientDrawable.Orientation.RIGHT_LEFT, colors)
            shape.shape = GradientDrawable.OVAL
            binding.shadowView.background = shape
        }

    }
}

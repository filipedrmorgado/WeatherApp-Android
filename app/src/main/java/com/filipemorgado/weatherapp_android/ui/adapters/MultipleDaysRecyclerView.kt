package com.filipemorgado.weatherapp_android.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.filipemorgado.weatherapp_android.data.model.WeatherDetail
import com.filipemorgado.weatherapp_android.databinding.WeatherDayItemBinding

class MultipleDaysRecyclerView : RecyclerView.Adapter<MultipleDaysRecyclerView.ViewHolder>() {

    private val weatherDetailList = ArrayList<WeatherDetail>()

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

    fun setData(newWeatherDetail: List<WeatherDetail>) {
        weatherDetailList.clear()
        weatherDetailList.addAll(newWeatherDetail)
        //todo we should notify only data that actually changed. Improve performance
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: WeatherDayItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindItems(weatherDetail: WeatherDetail) {
            binding.apply {
                //todo update with info
            }
        }
    }
}

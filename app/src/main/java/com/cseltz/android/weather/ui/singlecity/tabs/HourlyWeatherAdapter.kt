package com.cseltz.android.weather.ui.singlecity.tabs

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cseltz.android.weather.databinding.HourlyListItemBinding
import com.cseltz.android.weather.model.remote.remotedataclasses.weather.helpers.Hourly
import com.cseltz.android.weather.ui.uidataclasses.WeatherCity

class HourlyWeatherAdapter(
    private val weatherCity: WeatherCity
    ) : RecyclerView.Adapter<HourlyWeatherAdapter.HourlyWeatherViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourlyWeatherViewHolder {
        val binding = HourlyListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HourlyWeatherViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HourlyWeatherViewHolder, position: Int) {
        val currentHour = weatherCity.weatherParameters.hourly[position]
        holder.bind(currentHour)
    }

    override fun getItemCount(): Int {
        return weatherCity.weatherParameters.hourly.size
    }

    inner class HourlyWeatherViewHolder(private val binding: HourlyListItemBinding)
        : RecyclerView.ViewHolder(binding.root) {
            fun bind(hour: Hourly) {
                binding.hourlyTimeTextview.text = hour.getFormattedTime()
                binding.hourlyTempText.text = hour.getFormattedTemp()
                binding.hourlyWeatherIcon.setImageResource(hour.getWeatherIcon())
                binding.hourlyWindText.text = hour.getFormattedWind()
                binding.hourlyPopTextview.text = hour.getFormattedPop()
            }
        }
}
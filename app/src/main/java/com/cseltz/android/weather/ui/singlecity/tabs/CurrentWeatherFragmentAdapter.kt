package com.cseltz.android.weather.ui.singlecity.tabs

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cseltz.android.weather.databinding.DailyForecastListItemBinding
import com.cseltz.android.weather.model.remote.remotedataclasses.weather.helpers.Daily
import com.cseltz.android.weather.ui.uidataclasses.WeatherCity

class CurrentWeatherFragmentAdapter(
    private val weatherCity: WeatherCity
) : RecyclerView.Adapter<CurrentWeatherFragmentAdapter.CurrentWeatherViewHolder>() {

    override fun getItemCount(): Int {
        return weatherCity.weatherParameters.daily.size // Api gives 7 days of daily forecast
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrentWeatherViewHolder {
        val binding = DailyForecastListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CurrentWeatherViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CurrentWeatherViewHolder, position: Int) {
        val currentForecastDay = weatherCity.weatherParameters.daily[position]
        holder.bind(currentForecastDay)
    }


    inner class CurrentWeatherViewHolder(private val binding: DailyForecastListItemBinding)
        : RecyclerView.ViewHolder(binding.root) {
            fun bind(day: Daily) {
                binding.apply {
                    dailyForecastDate.text = day.getFormattedDate()
                    dailyForecastIcon.setImageResource(day.getDailyWeatherIcon())
                    dailyForecastTemperature.text = day.getFormattedTemp()
                    dailyForecastPop.text = day.getFormattedPop()
                }
            }
        }
}
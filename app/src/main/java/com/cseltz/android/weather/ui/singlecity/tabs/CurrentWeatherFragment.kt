package com.cseltz.android.weather.ui.singlecity.tabs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cseltz.android.weather.databinding.FragmentCurrentWeatherBinding
import com.cseltz.android.weather.ui.uidataclasses.WeatherCity
import java.text.SimpleDateFormat
import java.util.*

class CurrentWeatherFragment(private val weatherCity: WeatherCity): Fragment() {

    private lateinit var binding: FragmentCurrentWeatherBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCurrentWeatherBinding.inflate(inflater)

        binding.apply {
            timeTextview.text = getFormattedTime()
            weatherIcon.setImageResource(weatherCity.getCurrentWeatherIcon())
            tempTextview.text = weatherCity.getCurrentFormattedTemperature()
            descriptionTextview.text = weatherCity.weatherParameters.current.weather[0].description.replaceFirstChar { char -> char.uppercase() }
            feelsLikeTextview.text = weatherCity.getCurrentFormattedFeelsLikeTemp()
            windTextview.text = weatherCity.getCurrentFormattedWindString()
            windIcon.setImageResource(weatherCity.getCurrentWindIcon())
        }

        return binding.root
    }

    private fun getFormattedTime(): String {
        val formatter = SimpleDateFormat("hh:mm aa", Locale.US)
        return formatter.format(Date())
    }
}
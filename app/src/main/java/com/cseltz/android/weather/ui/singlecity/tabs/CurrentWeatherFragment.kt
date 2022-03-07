package com.cseltz.android.weather.ui.singlecity.tabs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cseltz.android.weather.databinding.FragmentCurrentWeatherBinding
import com.cseltz.android.weather.ui.singlecity.CURRENT_WEATHER_KEY
import com.cseltz.android.weather.ui.singlecity.SingleCityAdapter
import com.cseltz.android.weather.ui.uidataclasses.WeatherCity
import java.text.SimpleDateFormat
import java.util.*

class CurrentWeatherFragment: Fragment() {

    private lateinit var binding: FragmentCurrentWeatherBinding
    private lateinit var weatherCity: WeatherCity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCurrentWeatherBinding.inflate(inflater)

        weatherCity = arguments?.getParcelable(CURRENT_WEATHER_KEY)!!

        binding.apply {
            timeTextview.text = getFormattedTime()
            weatherIcon.setImageResource(weatherCity.getCurrentWeatherIcon())
            tempTextview.text = weatherCity.getCurrentFormattedTemperature()
            descriptionTextview.text = weatherCity.weatherParameters.current.weather[0].description.replaceFirstChar { char -> char.uppercase() }
            feelsLikeTextview.text = weatherCity.getCurrentFormattedFeelsLikeTemp()
            windTextview.text = weatherCity.getCurrentFormattedWindString()
            windIcon.setImageResource(weatherCity.getCurrentWindIcon())
        }

        // RecyclerView
        val rvAdapter = CurrentWeatherFragmentAdapter(weatherCity)
        binding.dailyRecyclerview.apply {
            adapter = rvAdapter
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }

        return binding.root
    }

    private fun getFormattedTime(): String {
        val formatter = SimpleDateFormat("hh:mm aa", Locale.US)
        return formatter.format(Date())
    }
}
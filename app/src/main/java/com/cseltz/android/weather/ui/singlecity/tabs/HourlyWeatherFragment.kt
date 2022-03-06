package com.cseltz.android.weather.ui.singlecity.tabs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cseltz.android.weather.databinding.FragmentHourlyWeatherBinding
import com.cseltz.android.weather.ui.uidataclasses.WeatherCity

class HourlyWeatherFragment(private val weatherCity: WeatherCity): Fragment() {

    private lateinit var binding: FragmentHourlyWeatherBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHourlyWeatherBinding.inflate(inflater)

        return binding.root
    }
}
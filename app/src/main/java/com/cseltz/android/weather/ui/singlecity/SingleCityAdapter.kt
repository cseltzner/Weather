package com.cseltz.android.weather.ui.singlecity

import android.os.Bundle
import android.util.Log
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.cseltz.android.weather.ui.singlecity.tabs.CurrentWeatherFragment
import com.cseltz.android.weather.ui.singlecity.tabs.HourlyWeatherFragment
import com.cseltz.android.weather.ui.uidataclasses.WeatherCity

private const val NUMBER_OF_TABS = 2
private const val TAG = "SingleCityAdapter"
const val CURRENT_WEATHER_KEY = "SingleCityAdapter.CurrentWeatherKey"
const val HOURLY_WEATHER_KEY = "SingleCityAdapter.HourlyWeatherKey"

// Viewpager adapter for changing tabs
class SingleCityAdapter(
    fragment: Fragment,
    private val weatherCity: WeatherCity
): FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return NUMBER_OF_TABS
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                CurrentWeatherFragment().apply {
                    arguments = bundleOf(CURRENT_WEATHER_KEY to weatherCity)
                }
            }
            1 -> {
                HourlyWeatherFragment().apply {
                    arguments = bundleOf(HOURLY_WEATHER_KEY to weatherCity)
                }
            }
            else -> {
                Log.d(TAG, "This fragment should not be showing...")
                CurrentWeatherFragment()
            }
        }
    }
}
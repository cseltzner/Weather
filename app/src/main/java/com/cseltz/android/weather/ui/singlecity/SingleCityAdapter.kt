package com.cseltz.android.weather.ui.singlecity

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.cseltz.android.weather.ui.singlecity.tabs.CurrentWeatherFragment
import com.cseltz.android.weather.ui.singlecity.tabs.HourlyWeatherFragment
import com.cseltz.android.weather.ui.uidataclasses.WeatherCity

private const val NUMBER_OF_TABS = 2
private const val TAG = "SingleCityAdapter"

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
            0 -> CurrentWeatherFragment(weatherCity)
            1 -> HourlyWeatherFragment(weatherCity)
            else -> {
                Log.d(TAG, "This fragment should not be showing...")
                CurrentWeatherFragment(weatherCity)
            }
        }
    }
}
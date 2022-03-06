package com.cseltz.android.weather.ui.singlecity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.cseltz.android.weather.MainActivity
import com.cseltz.android.weather.databinding.FragmentSingleCityBinding
import com.google.android.material.tabs.TabLayoutMediator

class SingleCityFragment: Fragment() {

    private lateinit var binding: FragmentSingleCityBinding
    private val args: SingleCityFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSingleCityBinding.inflate(inflater)

        (requireActivity() as MainActivity).supportActionBar?.title = "${args.weatherCity.city}, ${args.weatherCity.state}"

        val viewPagerAdapter = SingleCityAdapter(this, args.weatherCity)
        binding.viewPager.adapter = viewPagerAdapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = when(position) {
                0 -> "Current &\nForecast"
                1 -> "Hourly\nForecast"
                else -> "ERROR"
            }
        }.attach()

        return binding.root
    }
}
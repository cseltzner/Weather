package com.cseltz.android.weather.ui.singlecity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
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
        binding.viewPager.reduceDragSensitivity()
        binding.viewPager.offscreenPageLimit = 2

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

fun ViewPager2.reduceDragSensitivity(f: Int = 2) {
    val recyclerViewField = ViewPager2::class.java.getDeclaredField("mRecyclerView")
    recyclerViewField.isAccessible = true
    val recyclerView = recyclerViewField.get(this) as RecyclerView

    val touchSlopField = RecyclerView::class.java.getDeclaredField("mTouchSlop")
    touchSlopField.isAccessible = true
    val touchSlop = touchSlopField.get(recyclerView) as Int
    touchSlopField.set(recyclerView, touchSlop * f)
}
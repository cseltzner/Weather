package com.cseltz.android.weather.ui.maincitylist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cseltz.android.weather.databinding.FragmentMainCityListBinding
import com.cseltz.android.weather.databinding.MainCityListItemBinding
import com.cseltz.android.weather.ui.uidataclasses.WeatherCity

class MainCityListFragmentAdapter(
    private var itemList: List<WeatherCity>,
    private val listener: OnItemClickListeners
) : RecyclerView.Adapter<MainCityListFragmentAdapter.MainCityListFragmentViewHolder>() {

    fun submitList(list: List<WeatherCity>) {
        itemList = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MainCityListFragmentViewHolder {
        val binding = MainCityListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainCityListFragmentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainCityListFragmentViewHolder, position: Int) {
        val currentItem = itemList[position]
        holder.bind(currentItem)
    }


    inner class MainCityListFragmentViewHolder(private val binding: MainCityListItemBinding)
        : RecyclerView.ViewHolder(binding.root) {
            fun bind(weatherCity: WeatherCity) {
                binding.apply {
                    listItemCityName.text = weatherCity.city.replaceFirstChar { char -> char.uppercase() }
                    listItemIcon.setImageResource(weatherCity.getCurrentWeatherIcon())
                    listItemTemp.text = weatherCity.getCurrentFormattedTemperature()
                    listItemDescription.text = weatherCity.weatherParameters.current.weather[0].description.replaceFirstChar { char -> char.uppercase() }
                    root.setOnClickListener { listener.onClick(weatherCity) }
                    root.setOnLongClickListener {
                        listener.onLongClick(weatherCity)
                        true
                    }
                }
            }
        }


    interface OnItemClickListeners {
        fun onClick(weatherCity: WeatherCity)
        fun onLongClick(weatherCity: WeatherCity)
    }
}
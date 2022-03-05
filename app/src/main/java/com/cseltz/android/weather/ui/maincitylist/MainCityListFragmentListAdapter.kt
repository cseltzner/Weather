package com.cseltz.android.weather.ui.maincitylist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cseltz.android.weather.databinding.MainCityListItemBinding
import com.cseltz.android.weather.ui.uidataclasses.WeatherCity

// DO NOT USE. I'm leaving this here for historical purposes
// ListAdapter introduced bugs to the list while submitting
// lists, which were immediately fixed by using the normal
// adapter
// Now I'm setting it to private
private class MainCityListFragmentListAdapter(private val listener: OnClickListeners): ListAdapter<WeatherCity, MainCityListFragmentListAdapter.MainCityListFragmentViewHolder>(DiffUtilCallback()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MainCityListFragmentViewHolder {
        val binding = MainCityListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainCityListFragmentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainCityListFragmentViewHolder, position: Int) {
        val currentItem = getItem(position)
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
                }
            }

        }

    class DiffUtilCallback: DiffUtil.ItemCallback<WeatherCity>() {
        override fun areItemsTheSame(oldItem: WeatherCity, newItem: WeatherCity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: WeatherCity, newItem: WeatherCity): Boolean {
            return oldItem == newItem
        }
    }

    interface OnClickListeners {
        // OnClick events here
    }
}
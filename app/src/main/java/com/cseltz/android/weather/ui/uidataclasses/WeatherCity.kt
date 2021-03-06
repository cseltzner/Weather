package com.cseltz.android.weather.ui.uidataclasses

import android.os.Parcelable
import com.cseltz.android.weather.R
import com.cseltz.android.weather.model.remote.remotedataclasses.weather.WeatherObject
import kotlinx.parcelize.Parcelize

@Parcelize
data class WeatherCity(
    val id: Int,
    val city: String,
    val state: String,
    val weatherParameters: WeatherObject
): Parcelable {

    fun getCurrentWeatherIcon(): Int {
        return when (weatherParameters.current.weather[0].icon) {
            "01d" -> R.drawable.sunny
            "01n" -> R.drawable.clear_night
            "02d" -> R.drawable.partly_cloudy
            "02n" -> R.drawable.partly_cloudy_night
            "03d" -> R.drawable.cloudy
            "03n" -> R.drawable.cloudy
            "04d" -> R.drawable.cloudy
            "04n" -> R.drawable.cloudy
            "09d" -> R.drawable.rainy
            "09n" -> R.drawable.rainy
            "10d" -> R.drawable.rainy
            "10n" -> R.drawable.rainy
            "11d" -> R.drawable.thunderstorm
            "11n" -> R.drawable.thunderstorm
            "13d" -> R.drawable.snow
            "13n" -> R.drawable.snow
            "50d" -> R.drawable.fog
            "50n" -> R.drawable.fog
            else -> R.drawable.cloudy
        }
    }

    fun getCurrentFormattedTemperature(): String {
        val temp = weatherParameters.current.temp
        val formattedTemp = temp.toInt()
        return "${formattedTemp}\u00B0"
    }

    fun getCurrentFormattedFeelsLikeTemp(): String {
        val temp = weatherParameters.current.feels_like
        val formattedTemp = temp.toInt()
        return "${formattedTemp}\u00B0"
    }

    fun getCurrentFormattedWindString(): String {
        val windSpeed = weatherParameters.current.wind_speed
        val windDeg = weatherParameters.current.wind_deg

        val windDir =
            if ((windDeg in 0..21) || (windDeg in 338..360)) {
                "North"
            } else if (windDeg in 22..68) {
                "NE"
            } else if (windDeg in 69..113) {
                "East"
            } else if (windDeg in 114..158) {
                "SE"
            } else if (windDeg in 159..203) {
                "South"
            } else if (windDeg in 204..248) {
                "SW"
            } else if (windDeg in 249..293) {
                "West"
            } else if (windDeg in 294..337) {
                "NW"
            }
            else {
                ""
            }

        return "${windSpeed.toInt()} MPH $windDir"
    }

    fun getFormattedPop(): String {
        val pop = (weatherParameters.daily[0].pop * 100).toInt().toString()
        return "$pop%"
    }

    fun getFormattedHumidity(): String {
        val humidity = weatherParameters.current.humidity
        return "$humidity%"
    }

}

package com.cseltz.android.weather.model.remote.remotedataclasses.weather.helpers

import android.os.Parcelable
import android.util.Log
import com.cseltz.android.weather.R
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.util.*

@Parcelize
data class Daily(
    val clouds: Int,
    val dew_point: Double,
    @SerializedName("dt") val time: Long,
    val feels_like: FeelsLike,
    val humidity: Int,
    val moon_phase: Double,
    val moonrise: Long,
    val moonset: Long,
    val pop: Double, // Probability of precipitation
    val pressure: Int,
    val rain: Double? = 0.0,
    val snow: Double? = 0.0,
    val sunrise: Long,
    val sunset: Long,
    val temp: Temp,
    val uvi: Double,
    val weather: List<DailyWeather>,
    val wind_deg: Int,
    val wind_gust: Double? = 0.0,
    val wind_speed: Double
): Parcelable {

    fun getDailyWeatherIcon(): Int {
        return when (weather[0].icon) {
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

    fun getFormattedDate(): String {
        val formatter = SimpleDateFormat("EEE dd", Locale.US)
        val date = Date(time * 1000)
        return formatter.format(date)

    }

    fun getFormattedTemp(): String {
        val highTemp = temp.max.toInt()
        val lowTemp = temp.min.toInt()
        return "${highTemp}\u2109\n${lowTemp}\u2109"

    }

    // Pop = Probability of precipitation
    fun getFormattedPop(): String {
        val intPop = (pop * 100).toInt()
        return "${intPop}%"
    }

}
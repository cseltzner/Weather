package com.cseltz.android.weather.model.remote.remotedataclasses.weather.helpers

import android.os.Parcelable
import com.cseltz.android.weather.R
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.text.SimpleDateFormat
import java.util.*

@Parcelize
data class Hourly(
    val clouds: Int,
    val dew_point: Double,
    @SerializedName("dt") val time: Long,
    val feels_like: Double,
    val humidity: Int,
    val pop: Double, // Probability of precipitation
    val pressure: Int,
    val snow: Snow? = Snow(),
    val temp: Double,
    val uvi: Double,
    val visibility: Int,
    val rain: Rain? = Rain(),
    val weather: List<HourlyWeather>,
    val wind_deg: Int,
    val wind_gust: Double? = 0.0,
    val wind_speed: Double
): Parcelable {

    fun getWeatherIcon(): Int {
        return when (weather[0].icon) {
            "01d" -> R.mipmap.sunny
            "01n" -> R.mipmap.clear_night
            "02d" -> R.mipmap.partly_cloudy
            "02n" -> R.mipmap.partly_cloudy_night
            "03d" -> R.mipmap.cloudy
            "03n" -> R.mipmap.cloudy
            "04d" -> R.mipmap.cloudy
            "04n" -> R.mipmap.cloudy
            "09d" -> R.mipmap.rainy
            "09n" -> R.mipmap.rainy
            "10d" -> R.mipmap.rainy
            "10n" -> R.mipmap.rainy
            "11d" -> R.mipmap.thunderstorm
            "11n" -> R.mipmap.thunderstorm
            "13d" -> R.mipmap.snow
            "13n" -> R.mipmap.snow
            "50d" -> R.mipmap.fog
            "50n" -> R.mipmap.fog
            else -> R.mipmap.cloudy
        }
    }

    fun getFormattedTemp(): String {
        return "${temp.toInt()}\u2109"
    }

    fun getFormattedWind(): String {
        val windDir =
            if ((wind_deg in 0..21) || (wind_deg in 338..360)) {
                "North"
            } else if (wind_deg in 22..68) {
                "NE"
            } else if (wind_deg in 69..113) {
                "East"
            } else if (wind_deg in 114..158) {
                "SE"
            } else if (wind_deg in 159..203) {
                "South"
            } else if (wind_deg in 204..248) {
                "SW"
            } else if (wind_deg in 249..293) {
                "West"
            } else if (wind_deg in 294..337) {
                "NW"
            }
            else {
                ""
            }

        return "${wind_speed.toInt()} MPH\n${getCurrentWindClassifier().str}\n$windDir"
    }

    fun getFormattedPop(): String {
        return "${(pop * 100).toInt()}%"
    }

    fun getFormattedTime(): String {
        val formatter = SimpleDateFormat("EEE\nh a", Locale.US)
        val date = Date(time * 1000)
        return formatter.format(date)
    }

    private fun getCurrentWindClassifier(): WindClassifiers {
        return if (wind_speed >= 0 && wind_speed < 4) {
            WindClassifiers.LightBreeze
        } else if (wind_speed >= 4 && wind_speed < 13) {
            WindClassifiers.Breeze
        } else if (wind_speed >= 13 && wind_speed < 30) {
            WindClassifiers.Wind
        }
        else {
            WindClassifiers.StrongWind
        }
    }

    private sealed class WindClassifiers(val str: String) {
        object LightBreeze: WindClassifiers("light breeze")
        object Breeze: WindClassifiers("breeze")
        object Wind: WindClassifiers("wind")
        object StrongWind: WindClassifiers("strong wind")
    }

}
package com.cseltz.android.weather.model.remote.remotedataclasses.weather.helpers

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Hourly(
    val clouds: Int,
    val dew_point: Double,
    @SerializedName("dt") val time: Long,
    val feels_like: Double,
    val humidity: Int,
    val pop: Double, // Probability of precipitation
    val pressure: Int,
    val snow: Snow,
    val temp: Double,
    val uvi: Double,
    val visibility: Int,
    val weather: List<HourlyWeather>,
    val wind_deg: Int,
    val wind_gust: Double,
    val wind_speed: Double
): Parcelable
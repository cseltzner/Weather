package com.cseltz.android.weather.model.remote.remotedataclasses.weather.helpers

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Daily(
    val clouds: Int,
    val dew_point: Double,
    @SerializedName("dt") val time: Int,
    val feels_like: FeelsLike,
    val humidity: Int,
    val moon_phase: Double,
    val moonrise: Int,
    val moonset: Int,
    val pop: Double, // Probability of precipitation
    val pressure: Int,
    val rain: Double,
    val snow: Double,
    val sunrise: Int,
    val sunset: Int,
    val temp: Temp,
    val uvi: Double,
    val weather: List<DailyWeather>,
    val wind_deg: Int,
    val wind_gust: Double,
    val wind_speed: Double
): Parcelable
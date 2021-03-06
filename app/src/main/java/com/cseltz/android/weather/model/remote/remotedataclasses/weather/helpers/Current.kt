package com.cseltz.android.weather.model.remote.remotedataclasses.weather.helpers

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Current(
    val clouds: Int,
    val dew_point: Double,
    @SerializedName("dt") val time: Long,
    val feels_like: Double,
    val humidity: Int,
    val pressure: Int,
    val sunrise: Long,
    val sunset: Long,
    val temp: Double,
    val uvi: Double,
    val visibility: Int,
    val rain: Rain? = Rain(),
    val snow: Snow? = Snow(),
    val wind_gust: Double? = 0.0,
    val weather: List<CurrentWeather>,
    val wind_deg: Int,
    val wind_speed: Double
): Parcelable
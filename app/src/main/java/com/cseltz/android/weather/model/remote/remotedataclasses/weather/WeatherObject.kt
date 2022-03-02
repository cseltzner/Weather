package com.cseltz.android.weather.model.remote.remotedataclasses.weather

import android.os.Parcelable
import com.cseltz.android.weather.model.remote.remotedataclasses.weather.helpers.Current
import com.cseltz.android.weather.model.remote.remotedataclasses.weather.helpers.Daily
import com.cseltz.android.weather.model.remote.remotedataclasses.weather.helpers.Hourly
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class WeatherObject(
    val current: Current,
    val daily: List<Daily>,
    val hourly: List<Hourly>,
    @SerializedName("lat") val latitude: Double,
    @SerializedName("lon") val longitude: Double,
    val timezone: String,
    val timezone_offset: Int
): Parcelable
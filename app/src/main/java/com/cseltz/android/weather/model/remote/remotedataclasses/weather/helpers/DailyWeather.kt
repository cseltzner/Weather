package com.cseltz.android.weather.model.remote.remotedataclasses.weather.helpers

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DailyWeather(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
): Parcelable
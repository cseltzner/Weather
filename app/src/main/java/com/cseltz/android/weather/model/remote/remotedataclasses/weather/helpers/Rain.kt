package com.cseltz.android.weather.model.remote.remotedataclasses.weather.helpers

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Rain(
    val `1h` : Double? = 0.0
): Parcelable

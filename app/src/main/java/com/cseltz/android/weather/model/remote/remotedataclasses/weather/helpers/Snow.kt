package com.cseltz.android.weather.model.remote.remotedataclasses.weather.helpers

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Snow(
    val `1h`: Double
): Parcelable
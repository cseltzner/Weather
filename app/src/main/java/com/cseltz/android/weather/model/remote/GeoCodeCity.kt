package com.cseltz.android.weather.model.remote

import com.google.gson.annotations.SerializedName

data class GeoCodeCity(
    val country: String,
    @SerializedName("lat") val latitude: Double,
    val local_names: LocalNames,
    @SerializedName("lon") val longitude: Double,
    @SerializedName("name") val city: String,
    val state: String
)
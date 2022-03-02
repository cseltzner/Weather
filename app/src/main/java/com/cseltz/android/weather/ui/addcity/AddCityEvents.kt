package com.cseltz.android.weather.ui.addcity

sealed class AddCityEvents {
    data class OnCompletedFabClick(val city: String, val state: String): AddCityEvents()
}
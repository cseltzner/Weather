package com.cseltz.android.weather.ui.addcity

import com.cseltz.android.weather.model.remote.remotedataclasses.geocode.GeoCodeCity

sealed class AddCityUiEvents {
    object Loading: AddCityUiEvents()
    data class Failure(val message: String): AddCityUiEvents()
    object Success: AddCityUiEvents()
}

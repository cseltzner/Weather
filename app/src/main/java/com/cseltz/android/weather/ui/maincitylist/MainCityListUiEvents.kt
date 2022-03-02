package com.cseltz.android.weather.ui.maincitylist

import com.cseltz.android.weather.ui.uidataclasses.WeatherCity

sealed class MainCityListUiEvents {
    object Loading: MainCityListUiEvents()
    data class Success(val weatherCityList: List<WeatherCity>): MainCityListUiEvents()
    data class Failure(val message: String): MainCityListUiEvents()
    object NavigateToAddScreen: MainCityListUiEvents()
}

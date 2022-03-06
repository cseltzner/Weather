package com.cseltz.android.weather.ui.maincitylist

import com.cseltz.android.weather.ui.uidataclasses.WeatherCity

sealed class MainCityListEvents {
    object OnAddFabClicked: MainCityListEvents()
    data class OnRefreshClicked(val callback: (List<WeatherCity>) -> Unit): MainCityListEvents()
    object OnDeleteAllClicked: MainCityListEvents()
    object OnDeleteAllConfirmed: MainCityListEvents()
}

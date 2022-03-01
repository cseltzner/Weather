package com.cseltz.android.weather.model

import com.cseltz.android.weather.model.local.StoredCity
import com.cseltz.android.weather.model.local.StoredCityDao
import com.cseltz.android.weather.model.remote.api.OpenWeatherApi

class Repository(
    val storedCityDao: StoredCityDao,
    val openWeatherApi: OpenWeatherApi
) {

    suspend fun insertStoredCity(city: StoredCity) = storedCityDao.insertStoredCity(city)
    suspend fun deleteStoredCity(city: StoredCity) = storedCityDao.deleteStoredCity(city)
    suspend fun updateStoredCity(city: StoredCity) = storedCityDao.updateStoredCity(city)
    suspend fun getAllStoredCities() = storedCityDao.getAllStoredCities()
    suspend fun deleteAllStoredCities() = storedCityDao.deleteAll()

    fun getCoordinatesByLocationName(locationString: String) = openWeatherApi.getCoordinatesByLocationName(locationString)
}
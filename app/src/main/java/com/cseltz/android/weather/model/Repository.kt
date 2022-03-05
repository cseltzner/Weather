package com.cseltz.android.weather.model

import com.cseltz.android.weather.model.local.StoredCity
import com.cseltz.android.weather.model.local.StoredCityDao
import com.cseltz.android.weather.model.remote.api.OpenWeatherApi

class Repository(
    private val storedCityDao: StoredCityDao,
    private val openWeatherApi: OpenWeatherApi
) {

    // Local database functions
    suspend fun insertStoredCity(city: StoredCity) = storedCityDao.insertStoredCity(city)
    suspend fun deleteStoredCity(city: StoredCity) = storedCityDao.deleteStoredCity(city)
    suspend fun updateStoredCity(city: StoredCity) = storedCityDao.updateStoredCity(city)
    fun getAllStoredCitiesAsFlow() = storedCityDao.getAllStoredCitiesAsFlow()
    fun getAllStoredCitiesAsLiveData() = storedCityDao.getAllStoredCitiesAsLiveData()
    suspend fun getAllStoredCitiesAsList() = storedCityDao.getAllStoredCitiesAsList()
    suspend fun deleteAllStoredCities() = storedCityDao.deleteAll()

    // Api calls
    fun getCoordinatesByLocationName(locationString: String) = openWeatherApi.getCoordinatesByLocationName(locationString)
    fun getWeatherByCoordinates(latitude: Double, longitude: Double) = openWeatherApi.getWeather(
        latitude = latitude,
        longitude = longitude
    )
}
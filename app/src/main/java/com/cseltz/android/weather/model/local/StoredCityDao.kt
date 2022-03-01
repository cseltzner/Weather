package com.cseltz.android.weather.model.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface StoredCityDao {

    @Insert
    suspend fun insertStoredCity(city: StoredCity)

    @Delete
    suspend fun deleteStoredCity(city: StoredCity)

    @Update
    suspend fun updateStoredCity(city: StoredCity)

    @Query("SELECT * FROM stored_cities")
    fun getAllStoredCities(): Flow<List<StoredCity>>

    @Query("DELETE FROM stored_cities")
    suspend fun deleteAll()
}
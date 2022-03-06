package com.cseltz.android.weather.model.local

import androidx.lifecycle.LiveData
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
    fun getAllStoredCitiesAsFlow(): Flow<List<StoredCity>>

    @Query("SELECT * FROM stored_cities")
    fun getAllStoredCitiesAsLiveData(): LiveData<List<StoredCity>>

    @Query("SELECT * FROM stored_cities")
    suspend fun getAllStoredCitiesAsList(): List<StoredCity>

    @Query("DELETE FROM stored_cities")
    suspend fun deleteAll()

    @Query("DELETE FROM stored_cities WHERE id = :cityId")
    suspend fun deleteById(cityId: Int)
}
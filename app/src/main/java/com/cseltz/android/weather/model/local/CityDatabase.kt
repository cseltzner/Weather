package com.cseltz.android.weather.model.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [StoredCity::class], version = 1)
abstract class CityDatabase : RoomDatabase() {
    abstract fun storedCityDao(): StoredCityDao
}
package com.cseltz.android.weather.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "stored_cities")
data class StoredCity(
    val city: String,
    val state: String,
    val latitude: Double,
    val longitude: Double,

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)
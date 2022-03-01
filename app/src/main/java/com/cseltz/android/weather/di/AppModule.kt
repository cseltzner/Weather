package com.cseltz.android.weather.di

import android.app.Application
import androidx.room.Room
import com.cseltz.android.weather.model.Repository
import com.cseltz.android.weather.model.local.CityDatabase
import com.cseltz.android.weather.model.local.StoredCityDao
import com.cseltz.android.weather.model.remote.api.OpenWeatherApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideCityDatabase(
        app: Application
    ) = Room.databaseBuilder(app, CityDatabase::class.java, "city_db")
        .build()

    @Provides
    @Singleton
    fun provideStoredCityDao(
        db: CityDatabase
    ) = db.storedCityDao()

    @Provides
    @Singleton
    fun provideOpenWeatherApi() =
        Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(OpenWeatherApi::class.java)

    @Provides
    @Singleton
    fun provideAppRepository(
        dao: StoredCityDao,
        api: OpenWeatherApi
    ) = Repository(dao, api)
}

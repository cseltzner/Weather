package com.cseltz.android.weather.model.remote.api

import com.cseltz.android.weather.BuildConfig
import com.cseltz.android.weather.model.remote.remotedataclasses.geocode.GeoCodeCity
import com.cseltz.android.weather.model.remote.remotedataclasses.weather.WeatherObject
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface OpenWeatherApi {

   @GET("geo/1.0/direct")
   fun getCoordinatesByLocationName(
       @Query("q") location: String,
       @Query("appid") apiKey: String = BuildConfig.OPEN_WEATHER_API_KEY
   ): Call<List<GeoCodeCity>>

   @GET("data/2.5/onecall")
   fun getWeather(
       @Query("lat") latitude: Double,
       @Query("lon") longitude: Double,
       @Query("units") units: String = "imperial",
       @Query("exclude") exclusions: String = "minutely,alerts",
       @Query("appid") apiKey: String = BuildConfig.OPEN_WEATHER_API_KEY
   ): Call<WeatherObject>
}


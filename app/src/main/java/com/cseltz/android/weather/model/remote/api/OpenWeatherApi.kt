package com.cseltz.android.weather.model.remote.api

import com.cseltz.android.weather.BuildConfig
import com.cseltz.android.weather.model.remote.GeoCodeCity
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface OpenWeatherApi {

   @GET("geo/1.0/direct")
   fun getCoordinatesByLocationName(
       @Query("q") location: String,
       @Query("appid") apiKey: String = BuildConfig.OPEN_WEATHER_API_KEY
   ): Call<List<GeoCodeCity>>
}
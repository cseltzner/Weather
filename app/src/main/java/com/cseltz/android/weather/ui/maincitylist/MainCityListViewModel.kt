package com.cseltz.android.weather.ui.maincitylist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.cseltz.android.weather.model.Repository
import com.cseltz.android.weather.model.remote.remotedataclasses.weather.WeatherObject
import com.cseltz.android.weather.ui.uidataclasses.WeatherCity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

private const val TAG = "MainCityListViewModel"

@HiltViewModel
class MainCityListViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    /*
    * Private channel for sending events to the Ui
    * Public flow for Ui to collect UiEvents
     */
    private val _uiEvent = Channel<MainCityListUiEvents>()
    val uiEvent = _uiEvent.receiveAsFlow()

    /*
    * List of all database's stored cities, received as a LiveData for observation by Ui
     */
    val cityList = repository.storedCityDao.getAllStoredCities().asLiveData()

    fun performEvent(event: MainCityListEvents) {
        when (event) {
            is MainCityListEvents.OnAddFabClicked -> {
                viewModelScope.launch {
                    _uiEvent.send(MainCityListUiEvents.NavigateToAddScreen)
                }
            }
        }
    }

    // Returns list of Ui WeatherCity objects from api
    // Or empty list if the city list is empty
    fun requireUpdatedList() {
        viewModelScope.launch {
            val weatherCityList = mutableListOf<WeatherCity>()
            if (!cityList.value.isNullOrEmpty()) {
                Log.d(TAG, "Fetching Weathercity...")
                _uiEvent.send(MainCityListUiEvents.Loading)
                for (city in cityList.value!!) {
                    val lat = city.latitude
                    val lon = city.longitude
                    Log.d(TAG, "Latitude: $lat, Longitude: $lon")
                    val weatherObjectCall = repository.openWeatherApi.getWeather(lat, lon)
                    weatherObjectCall.enqueue(object : Callback<WeatherObject> {
                        override fun onResponse(
                            call: Call<WeatherObject>,
                            response: Response<WeatherObject>
                        ) {
                            Log.d(TAG, "OnResponse")
                            if (response.isSuccessful && response.body() != null) {
                                Log.d(TAG, "Response is successful")
                                val weatherObject = response.body()!!
                                val weatherCity = WeatherCity(
                                    id = city.id,
                                    city = city.city,
                                    state = city.state,
                                    weatherParameters = weatherObject
                                )
                                weatherCityList.add(weatherCity)
                                Log.d(TAG, "WeatherCity added")
                                Log.d(TAG, "Current weathercity list $weatherCityList")
                                viewModelScope.launch {
                                    Log.d(TAG, "WeatherCityList = ${weatherCityList}")
                                    _uiEvent.send(MainCityListUiEvents.Success(weatherCityList))
                                }

                            }


                        }

                        override fun onFailure(call: Call<WeatherObject>, t: Throwable) {
                            viewModelScope.launch {
                                Log.d(TAG, "Failure")
                                _uiEvent.send(MainCityListUiEvents.Failure("Unable to load weather, please check your connection and try again"))
                                Log.d(TAG, t.message.toString())
                            }
                        }
                    })
                }

            }


        }
    }
}
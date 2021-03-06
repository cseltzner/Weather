package com.cseltz.android.weather.ui.maincitylist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.cseltz.android.weather.model.Repository
import com.cseltz.android.weather.model.local.StoredCity
import com.cseltz.android.weather.model.remote.remotedataclasses.weather.WeatherObject
import com.cseltz.android.weather.ui.uidataclasses.WeatherCity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
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
    val cityList = repository.getAllStoredCitiesAsLiveData()
    private var _cityListCheck = mutableListOf<StoredCity>()

    private val weatherCityList = mutableListOf<WeatherCity>()

    var lastUpdatedTime = ""

    fun performEvent(event: MainCityListEvents) {
        when (event) {
            is MainCityListEvents.OnAddFabClicked -> {
                viewModelScope.launch {
                    _uiEvent.send(MainCityListUiEvents.NavigateToAddScreen)
                }
            }

            is MainCityListEvents.OnRefreshClicked -> {
                retrieveUpdatedList(event.callback)
            }

            is MainCityListEvents.OnDeleteAllClicked -> {
                viewModelScope.launch {
                    _uiEvent.send(MainCityListUiEvents.NavigateToDeleteAllDialog)
                }
            }

            is MainCityListEvents.OnDeleteAllConfirmed -> {
                viewModelScope.launch {
                    repository.deleteAllStoredCities()
                    weatherCityList.clear()
                    Log.d(TAG, "Deleted all")
                    _uiEvent.send(MainCityListUiEvents.DeleteAllSuccess)
                    lastUpdatedTime = ""
                }
            }

            is MainCityListEvents.OnDeleteCityClicked -> {
                viewModelScope.launch {
                    repository.deleteStoredCityById(event.city.id)
                    weatherCityList.remove(event.city)
                }
            }
        }
    }

    // Calls api for most recent list if the database was updated, or viewModel list is empty
    // Otherwise sends viewModel's list
    fun getWeatherCityList(onSuccess: (List<WeatherCity>) -> Unit) {
        if (cityList.value != _cityListCheck as List<StoredCity>) {
            Log.d(TAG, "Getting new city list")
            retrieveUpdatedList(onSuccess)
        } else {
            Log.d(TAG, "Not getting new citylist")
            onSuccess(weatherCityList)
        }
    }

    fun getUpdatedTime(): String {
        val formatter = SimpleDateFormat("EEE, MMM d hh:mm aaa", Locale.US)
        val time = formatter.format(Date())
        lastUpdatedTime = time
        return time
    }


    // Performs callback with list of Ui WeatherCity objects from api
    // Or empty list if the city list is empty
    private fun retrieveUpdatedList(onSuccess: (List<WeatherCity>) -> Unit) {
        val weatherCities = mutableListOf<WeatherCity>()
        var callsCompleted = 0
        viewModelScope.launch {
            if (!cityList.value.isNullOrEmpty()) {
                Log.d(TAG, "Fetching Weathercity...")
                _uiEvent.send(MainCityListUiEvents.Loading)
                for (city in cityList.value!!) {
                    val lat = city.latitude
                    val lon = city.longitude
                    Log.d(TAG, "Latitude: $lat, Longitude: $lon")
                    val weatherObjectCall = repository.getWeatherByCoordinates(lat, lon)
                    weatherObjectCall.enqueue(object : Callback<WeatherObject> {
                        override fun onResponse(
                            call: Call<WeatherObject>,
                            response: Response<WeatherObject>
                        ) {
                            Log.d(TAG, "OnResponse")
                            if (response.isSuccessful && response.body() != null) {
                                Log.d(TAG, "Response is successful ${city.city}")
                                val weatherObject = response.body()!!
                                val weatherCity = WeatherCity(
                                    id = city.id,
                                    city = when (city.city) {
                                        "Gold Canyon RV & Golf Resort" -> {
                                            "Gold Canyon" // Yes this is hacky but I plan on adding a rename feature later, and want the app to look good for now TODO add rename feature
                                        }
                                        else -> city.city
                                    },
                                    state = city.state,
                                    weatherParameters = weatherObject
                                )
                                weatherCities.add(weatherCity)
                                Log.d(TAG, "WeatherCity added")
                                callsCompleted++

                                if (callsCompleted == cityList.value!!.size) {
                                    Log.d(TAG, "Success message sent")
                                    viewModelScope.launch {
                                        _cityListCheck.clear()
                                        _cityListCheck.addAll(cityList.value!!)
                                        weatherCityList.clear()
                                        weatherCityList.addAll(weatherCities)
                                        _uiEvent.send(MainCityListUiEvents.Success)
                                        _uiEvent.send(MainCityListUiEvents.UpdateRefreshTime)
                                        onSuccess(weatherCityList as List<WeatherCity>) // Maybe send as event?
                                    }
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

            } else {
                Log.d(TAG, "Performing onSuccess with empty list")
                onSuccess(listOf())
            }

        }
    }

    private fun getFormattedTime(): String {
        val formatter = SimpleDateFormat("EEE, MMM d hh:mm aaa", Locale.US)
        return formatter.format(Date())
    }

}
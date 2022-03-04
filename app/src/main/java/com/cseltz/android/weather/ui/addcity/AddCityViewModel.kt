package com.cseltz.android.weather.ui.addcity

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.cseltz.android.weather.model.Repository
import com.cseltz.android.weather.model.local.StoredCity
import com.cseltz.android.weather.model.remote.remotedataclasses.geocode.GeoCodeCity
import com.cseltz.android.weather.util.StateIdToStateCodeConverter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

private const val TAG = "AddCityViewModel"

@HiltViewModel
class AddCityViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    var currentStateSpinnerPosition: Int = 0
    var cityEditTextValue = ""

    private val _uiEvent = Channel<AddCityUiEvents>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun performEvent(event: AddCityEvents) {
        when (event) {
            is AddCityEvents.OnCompletedFabClick -> {
                viewModelScope.launch {

                    val dbCityList = repository.storedCityDao.getAllStoredCitiesAsList()
                    var shouldContinue = true
                    Log.d(TAG, "Checking db citylist...")
                    Log.d(TAG, "db city list contents: ${dbCityList.toString()}")
                    for (city in dbCityList) {
                        Log.d(TAG, "Values: " +
                                "\n${city.city.lowercase().trim()}" +
                                "\n${event.city.lowercase().trim()}" +
                                "\n${city.state.lowercase().trim()}" +
                                "\n${event.state.lowercase().trim()}")
                        if (city.city.lowercase().trim() == event.city.lowercase().trim() && city.state.lowercase().trim() == StateIdToStateCodeConverter.stateAbbreviationToState(event.state).lowercase().trim()) {
                            shouldContinue = false
                        }
                    }

                    if (shouldContinue) {
                        _uiEvent.send(AddCityUiEvents.Loading)
                        val apiCall = repository.openWeatherApi.getCoordinatesByLocationName(
                            getLocationString(
                                event.city,
                                event.state
                            )
                        )
                        apiCall.enqueue(object : Callback<List<GeoCodeCity>> {
                            override fun onResponse(
                                call: Call<List<GeoCodeCity>>,
                                response: Response<List<GeoCodeCity>>
                            ) {
                                if (response.isSuccessful && !response.body().isNullOrEmpty()) {
                                    addCityToDatabase(response.body()!!)
                                    viewModelScope.launch {
                                        _uiEvent.send(AddCityUiEvents.Success)
                                    }
                                } else {
                                    viewModelScope.launch {
                                        _uiEvent.send(AddCityUiEvents.Failure("Unable to find city. Check the spelling and try again"))
                                    }
                                }
                            }

                            override fun onFailure(call: Call<List<GeoCodeCity>>, t: Throwable) {
                                viewModelScope.launch {
                                    _uiEvent.send(AddCityUiEvents.Failure("Network error. Please check your connection and try again"))
                                }
                            }
                        })
                    } else {
                        viewModelScope.launch {
                            _uiEvent.send(AddCityUiEvents.Failure("You are already tracking ${event.city.replaceFirstChar { char -> char.uppercase() }}!"))
                        }
                    }
                }
            }
        }
    }

    fun validateCity(city: String, spinnerPos: Int): Boolean {
        if (city.isNotBlank() && spinnerPos >= 0 && spinnerPos <= 49) {
            return true
        }
        return false
    }

    private fun getLocationString(city: String, state: String): String {
        return "$city,$state,us"
    }

    private fun addCityToDatabase(list: List<GeoCodeCity>) {
        val city = list[0]
        val cityToStore = StoredCity(
            city = city.city,
            state = city.state,
            latitude = city.latitude,
            longitude = city.longitude
        )
        viewModelScope.launch {
            repository.storedCityDao.insertStoredCity(cityToStore)
        }
    }


}
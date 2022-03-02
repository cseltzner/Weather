package com.cseltz.android.weather.ui.addcity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cseltz.android.weather.model.Repository
import com.cseltz.android.weather.model.local.StoredCity
import com.cseltz.android.weather.model.remote.remotedataclasses.geocode.GeoCodeCity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

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
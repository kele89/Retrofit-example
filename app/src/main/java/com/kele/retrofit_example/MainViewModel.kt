package com.kele.retrofit_example

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.kele.retrofit_example.model.WeatherResponse
import com.kele.retrofit_example.network.ApiResponse
import com.kele.retrofit_example.network.OpenWeatherApiManager
import com.kele.retrofit_example.data.WeatherRepository

/**
 * Το ViewModel είναι το Business Layer της εφαρμογής
 */
class MainViewModel : ViewModel() {

    /**
     * Το weatherResponse είναι αωτικείμενο LiveData το οποίο μπορεί να παρατηρείται από το View.
     */
    private val _weatherResponse = MediatorLiveData<ApiResponse<WeatherResponse>>()
    val weatherResponse: LiveData<ApiResponse<WeatherResponse>> = _weatherResponse

    private val manager = OpenWeatherApiManager()
    private val api = manager.getService()
    private val repository: WeatherRepository =
        WeatherRepository(api)

    /**
     * Οι παρακάτω μέθοδοι καλούν το repository και έτσι συνδέεται το Business Layer με το Data Layer.
     * Παίρνουμε την απάντηση από το repository και την τοποθετούμε στο LiveData το οποίο στέλνει
     * τα δεδομένα στο View.
     */
    fun fetchWeatherByLocation(lat: String, lon: String) {
        _weatherResponse.addSource(
            repository.getWeatherByLocation(lat, lon)
        ) {
            _weatherResponse.value = it
        }

    }

    fun fetchWeatherByCityName(cityName: String) {
        _weatherResponse.addSource(
            repository.getWeatherByCityName(cityName)
        ) {
            _weatherResponse.value = it
        }

    }
}
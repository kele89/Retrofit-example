package com.kele.retrofit_example

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kele.retrofit_example.model.WeatherResponse
import com.kele.retrofit_example.network.ApiResponse
import com.kele.retrofit_example.network.OpenWeatherApiManager
import com.kele.retrofit_example.network.WeatherRepository
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MainViewModel : ViewModel() {

    private val _weatherResponse = MediatorLiveData<ApiResponse<WeatherResponse>>()

    val weatherResponse: LiveData<ApiResponse<WeatherResponse>> = _weatherResponse

    private val parentJob = Job()

    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Default

    private val scope = CoroutineScope(coroutineContext)

    private val manager = OpenWeatherApiManager()

    private val api = manager.getService()

    private val repository: WeatherRepository = WeatherRepository(api)



    fun fetchWeather(lat: String, lon: String, appid: String) {
        _weatherResponse.addSource(
            repository.getWeather(lat, lon, appid)
        ) {
            _weatherResponse.value = it
        }

    }
}
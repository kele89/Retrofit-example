package com.kele.retrofit_example

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kele.retrofit_example.model.WeatherResponse
import com.kele.retrofit_example.network.OpenWeatherApiManager
import com.kele.retrofit_example.network.WeatherRepository
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MainViewModel : ViewModel() {

    val weatherLiveData = MutableLiveData<WeatherResponse>()

    private val parentJob = Job()

    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Default

    private val scope = CoroutineScope(coroutineContext)

    private val manager = OpenWeatherApiManager()

    private val api = manager.getService()

    private val repository: WeatherRepository = WeatherRepository(api)



    fun fetchWeather(lat: String, lon: String, appid: String) {
        scope.launch {
            val weather = repository.getWeather(lat, lon, appid)
            weatherLiveData.postValue(weather)
        }
    }
}
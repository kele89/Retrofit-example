package com.kele.retrofit_example.network

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kele.retrofit_example.BuildConfig
import com.kele.retrofit_example.model.WeatherResponse
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.awaitResponse

class WeatherRepository(private val api: OpenWeatherApi) : BaseRepository() {

    companion object {
        private const val HTTP_STATUS_OK = 200
        private const val HTTP_STATUS_CREATED = 201
    }

    fun getWeatherByLocation(lat: String, lon:String): LiveData<ApiResponse<WeatherResponse>> {
        val result = MutableLiveData<ApiResponse<WeatherResponse>>()
        GlobalScope.launch {
            try {
                val apiResponse = api.getWeatherInfoByLocation(lat, lon, BuildConfig.OPEN_WEATHER_API_KEY).awaitResponse()
                when (apiResponse.code()) {
                    HTTP_STATUS_OK, HTTP_STATUS_CREATED -> {
                        result.postValue(ApiResponse.success(apiResponse.body()))
                    }
                    else -> {
                        result.postValue(ApiResponse.error(0))
                    }
                }
            } catch (e: Exception) {
                result.postValue(ApiResponse.error(0, e.message))
            }
        }
        return result
    }

    fun getWeatherByCityName(cityName:String): LiveData<ApiResponse<WeatherResponse>> {
        val result = MutableLiveData<ApiResponse<WeatherResponse>>()
        GlobalScope.launch {
            try {
                val apiResponse = api.getWeatherInfoByCityName(cityName, BuildConfig.OPEN_WEATHER_API_KEY).awaitResponse()
                when (apiResponse.code()) {
                    HTTP_STATUS_OK, HTTP_STATUS_CREATED -> {
                        result.postValue(ApiResponse.success(apiResponse.body()))
                    }
                    else -> {
                        result.postValue(ApiResponse.error(0))
                    }
                }
            } catch (e: Exception) {
                result.postValue(ApiResponse.error(0, e.message))
            }
        }
        return result
    }

}
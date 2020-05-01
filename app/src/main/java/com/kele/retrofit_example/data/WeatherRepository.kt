package com.kele.retrofit_example.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kele.retrofit_example.BuildConfig
import com.kele.retrofit_example.model.WeatherResponse
import com.kele.retrofit_example.network.ApiResponse
import com.kele.retrofit_example.network.OpenWeatherApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.awaitResponse

/**
 * Το Repository είναι το Data Layer.
 */
class WeatherRepository(private val api: OpenWeatherApi) {

    companion object {
        private const val HTTP_STATUS_OK = 200
        private const val HTTP_STATUS_CREATED = 201
    }

    /**
     * Οι παρακάτω μέθοδοι καλούν το API και επιστρέφουν LiveData εκεί που τα καλούν. Στην
     * περίπτωσή μας το ViewModel.
     * To GlobalScope είναι ο τρόπος που χεοριζόμαστε τα Coroutines της Kotlin.
     * Στην ουσία καλούμε το API σε background thread για να μην μπλοκάρουμε το thread στο
     * οποίο τρέχει το UI.
     * Όταν παίρνουμε response το περνάμε στα LiveData τα οποία ανανεώνουν το ViewModel, το
     * οποίο με την σειρά του ανανεώνει το observable LiveData που παρακολουθεί το View.
     */
    fun getWeatherByLocation(lat: String, lon:String): LiveData<ApiResponse<WeatherResponse>> {
        val result = MutableLiveData<ApiResponse<WeatherResponse>>()
        GlobalScope.launch {
            try {
                val apiResponse = api.getWeatherInfoByLocation(lat, lon, BuildConfig.OPEN_WEATHER_API_KEY).awaitResponse()
                when (apiResponse.code()) {
                    HTTP_STATUS_OK, HTTP_STATUS_CREATED -> {
                        result.postValue(
                            ApiResponse.success(
                                apiResponse.body()
                            )
                        )
                    }
                    else -> {
                        result.postValue(
                            ApiResponse.error(
                                0
                            )
                        )
                    }
                }
            } catch (e: Exception) {
                result.postValue(
                    ApiResponse.error(
                        0,
                        e.message
                    )
                )
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
                        result.postValue(
                            ApiResponse.success(
                                apiResponse.body()
                            )
                        )
                    }
                    else -> {
                        result.postValue(
                            ApiResponse.error(
                                0
                            )
                        )
                    }
                }
            } catch (e: Exception) {
                result.postValue(
                    ApiResponse.error(
                        0,
                        e.message
                    )
                )
            }
        }
        return result
    }

}
package com.kele.retrofit_example.network

import com.kele.retrofit_example.model.WeatherResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherRepository(private val api: OpenWeatherApi) : BaseRepository() {

    fun getWeather(lat: String, lon:String, appid:String): WeatherResponse? {
        var weatherResponse = WeatherResponse()
        api.getWeatherInfo(lat, lon, appid).enqueue(object : Callback<WeatherResponse> {
            override fun onResponse(
                call: Call<WeatherResponse>,
                response: Response<WeatherResponse>
            ) {
                if (response.code() == 200) {
                    weatherResponse = response.body()!!
                }
            }
            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
            }
        })
        return weatherResponse
    }
}
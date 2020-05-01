package com.kele.retrofit_example.network

import com.kele.retrofit_example.OpenWeatherInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class OpenWeatherApiManager {

    companion object {
        private const val URL = "https://api.openweathermap.org/data/2.5/"
    }

    fun getService(): OpenWeatherApi {
        val client = OkHttpClient().newBuilder()
            .addInterceptor(OpenWeatherInterceptor())
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(OpenWeatherApi::class.java)
    }

}
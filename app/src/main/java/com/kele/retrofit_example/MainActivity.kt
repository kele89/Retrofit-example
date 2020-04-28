package com.kele.retrofit_example

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import com.kele.retrofit_example.network.OpenWeatherApiManager

class MainActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        main()
    }

    private fun main() {

        mainViewModel = MainViewModel()

        mainViewModel.fetchWeather("35","139","131b689aedf7867515859a18e6eeecb0")

        mainViewModel.weatherLiveData.observe(this, Observer {
            val result = it
        })
    }
}

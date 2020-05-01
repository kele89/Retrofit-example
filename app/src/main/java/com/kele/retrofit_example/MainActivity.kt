package com.kele.retrofit_example

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.lifecycle.Observer
import com.kele.retrofit_example.model.WeatherResponse
import com.kele.retrofit_example.network.ApiResponse
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        main()
    }

    private fun main() {
        mainViewModel = MainViewModel()

        bt_get_weather.setOnClickListener {
            mainViewModel.fetchWeatherByLocation("35","139","131b689aedf7867515859a18e6eeecb0")
        }

        sp_country_list.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedItem = parent?.getItemAtPosition(position).toString()
                mainViewModel.fetchWeatherByCityName(selectedItem,"131b689aedf7867515859a18e6eeecb0")
            }

        }

        mainViewModel.weatherResponse.observe(this, Observer { response ->
            when(response) {
                is ApiResponse.Success -> applyInformationToScreen(response.data)
            }
        })
    }

    private fun applyInformationToScreen(data: WeatherResponse?) {
        if (data != null) {
            tv_country.text = "Country: " + data.sys?.country
            tv_temperature.text = "Temperature: " + data.main?.temp.toString()
            tv_humidity.text = "Humidity: " + data.main?.humidity.toString()
            tv_pressure.text = "Pressure: " + data.main?.pressure.toString()
            tv_temp_min.text = "Minimum Temperature: " + data.main?.temp_min.toString()
            tv_temp_max.text = "Maximum Temperature: " + data.main?.temp_max.toString()
        }
    }
}

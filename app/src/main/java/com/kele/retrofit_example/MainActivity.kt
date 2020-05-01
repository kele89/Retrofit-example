package com.kele.retrofit_example

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import com.kele.retrofit_example.model.WeatherResponse
import com.kele.retrofit_example.network.ApiResponse
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel

    private var locationManager : LocationManager? = null

    private val locationListener: LocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            mainViewModel.fetchWeatherByLocation(location.latitude.toString(), location.longitude.toString())
        }
        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
        override fun onProviderEnabled(provider: String) {}
        override fun onProviderDisabled(provider: String) {}
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager?
        main()
    }

    private fun main() {
        mainViewModel = MainViewModel()

        bt_get_weather.setOnClickListener {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                val permissions = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
                ActivityCompat.requestPermissions(this, permissions,0)
            } else {
                locationManager?.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,
                    0L,
                    0f,
                    locationListener
                )
            }
        }

        sp_country_list.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedItem = parent?.getItemAtPosition(position).toString()
                mainViewModel.fetchWeatherByCityName(selectedItem)
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

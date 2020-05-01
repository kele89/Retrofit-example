package com.kele.retrofit_example.model

import com.google.gson.annotations.SerializedName

/**
 * Αυτό είναι το DataModel το οποίο χρησιμοποιεί το SerializedName annotation.
 * Στην ουσεία κάνει map τα στοιχεία που υπάρχουν στο JSON response σε μεταβλητές της κλάσης.
 */
class WeatherResponse {
    @SerializedName("sys")
    var sys: Sys? = null
    @SerializedName("main")
    var main: Main? = null
}

class Main {
    @SerializedName("temp")
    var temp: Float = 0.0f
    @SerializedName("humidity")
    var humidity: Float = 0.0f
    @SerializedName("pressure")
    var pressure: Float = 0.0f
    @SerializedName("temp_min")
    var temp_min: Float = 0.0f
    @SerializedName("temp_max")
    var temp_max: Float = 0.0f
}

class Sys {
    @SerializedName("country")
    var country: String? = null
}
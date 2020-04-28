package com.kele.retrofit_example

import okhttp3.Interceptor
import okhttp3.Response

class OpenWeatherInterceptor(private val apiKey: String) :
    Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val url = request.url().newBuilder()
            .addQueryParameter("api_key", apiKey)
            .build()

        val newRequest = request.newBuilder()
            .url(url)
//            .addHeader("Cache-Control", "public, max-age=$cacheDuration")
            .build()

        return chain.proceed(newRequest)
    }
}
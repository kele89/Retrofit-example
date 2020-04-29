package com.kele.retrofit_example.network

sealed class ApiResponse<T> {
    data class Loading<T>(var loading: Boolean, var message: String?) : ApiResponse<T>()
    data class Success<T>(var data: T?, var message: String?) : ApiResponse<T>()
    data class Error<T>(var errorCode: Int, var errorMessage: String?) : ApiResponse<T>()
    data class Canceled<T>(var message: String?) : ApiResponse<T>()

    companion object {
        fun <T> loading(isLoading: Boolean, message: String?): ApiResponse<T> = Loading(isLoading, message)

        fun <T> success(data: T?, message: String? = null): ApiResponse<T> = Success(data, message)

        fun <T> error(errorCode: Int = 0, errorMessage: String? = null): ApiResponse<T> = Error(errorCode, errorMessage)

        fun <T> cancelled(message: String? = null): ApiResponse<T> = Canceled(message)
    }
}
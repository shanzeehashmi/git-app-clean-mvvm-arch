package com.zeeshan.gitapp.common

sealed class NetworkResult<T : Any> {
    class Success<T : Any>(val data: T) : NetworkResult<T>()
    class Error<T : Any>(val errorCode: Int, val errorMessage: String) : NetworkResult<T>()
    class UnknownError<T : Any>() : NetworkResult<T>()
    class Exception<T : Any>(val error: Throwable): NetworkResult<T>()
}

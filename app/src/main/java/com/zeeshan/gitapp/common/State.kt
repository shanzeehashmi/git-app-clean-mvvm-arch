package com.zeeshan.gitapp.common

/**
 * generic state which can be used throughout the app
 *
 * LOADING
 * SUCCESS
 * ERROR
 */
sealed class State<T>(val data: T? = null, val message: String? = null) {

    class Loading<T> : State<T>()
    class Success<T>(data: T?) : State<T>(data)
    class Error(message: String?): State<String>(message)

}
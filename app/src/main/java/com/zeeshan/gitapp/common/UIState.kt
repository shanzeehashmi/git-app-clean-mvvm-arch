package com.zeeshan.gitapp.common

/**
 * generic state which can be used throughout the app
 *
 * LOADING
 * SUCCESS
 * ERROR
 */
sealed class UIState<T>(val data: T? = null, val message: String? = null) {

    class Loading<T> : UIState<T>()
    class Success<T>(data: T?) : UIState<T>(data)
    class Error(message: String?): UIState<String>(message)

}
package com.zeeshan.gitapp.data.data_source.remote.network.adapters.networkrequest

import com.google.gson.Gson
import com.zeeshan.gitapp.common.NetworkResult
import com.zeeshan.gitapp.data.data_source.remote.network.error.NetworkErrorBody
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.components.SingletonComponent
import okhttp3.Request
import okhttp3.ResponseBody
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Converter
import retrofit2.Response
import javax.inject.Inject

internal class NetworkResultCall<T : Any>(
    private val proxy: Call<T>
) : Call<NetworkResult<T>> {

    override fun enqueue(callback: Callback<NetworkResult<T>>) {

        proxy.enqueue(
            object : Callback<T> {
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    val body = response.body()

                    if (response.isSuccessful) {

                        body?.apply {
                            callback.onResponse(
                                this@NetworkResultCall,
                                Response.success(NetworkResult.Success(body))
                            )
                        } ?: callback.onResponse(
                            this@NetworkResultCall,
                            Response.success(NetworkResult.UnknownError())
                        )

//                        if (body != null) {
//                            callback.onResponse(
//                                this@NetworkResultCall,
//                                Response.success(NetworkResult.Success(body))
//                            )
//                        } else {
//                            // Body is null even though api is successful, so return the unknown error
//                            callback.onResponse(
//                                this@NetworkResultCall,
//                                Response.success(NetworkResult.UnknownError())
//                            )
//                        }
                    } else {
                        var errorMessage: String = ""
                        response.errorBody()?.let{
                            errorMessage = Gson().fromJson(it.string(), NetworkErrorBody::class.java).errorMessage ?: ""
                        }

                        callback.onResponse(
                            this@NetworkResultCall,
                            Response.success(
                                NetworkResult.Error(
                                    errorCode = response.code(),
                                    errorMessage = errorMessage
                                )
                            )
                        )
                    }
                }

                override fun onFailure(call: Call<T>, t: Throwable) {
                    callback.onResponse(
                        this@NetworkResultCall,
                        Response.success(NetworkResult.Exception(t))
                    )
                }

            }
        )

    }


    override fun execute(): Response<NetworkResult<T>> = throw NotImplementedError()
//    =
//        runBlocking(coroutineScope.coroutineContext) {
//            val result = RemoteApi.handleApi { proxy.awaitResponse() }
//
//            Response.success(result)
//        }

    override fun clone(): Call<NetworkResult<T>> = NetworkResultCall(proxy.clone())
    override fun request(): Request = proxy.request()
    override fun timeout(): Timeout = proxy.timeout()
    override fun isExecuted(): Boolean = proxy.isExecuted
    override fun isCanceled(): Boolean = proxy.isCanceled
    override fun cancel() = proxy.cancel()

}
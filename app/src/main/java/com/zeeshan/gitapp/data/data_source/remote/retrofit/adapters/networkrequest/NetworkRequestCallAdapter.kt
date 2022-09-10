package com.zeeshan.gitapp.data.data_source.remote.retrofit

import com.zeeshan.gitapp.common.NetworkResult
import com.zeeshan.gitapp.data.data_source.remote.retrofit.adapters.networkrequest.NetworkResultCall
import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type

class NetworkResultCallAdapter(
    private val resultType: Type
) : CallAdapter<Type, Call<NetworkResult<Type>>> {

    override fun responseType(): Type = resultType

    override fun adapt(call: Call<Type>): Call<NetworkResult<Type>> {
        return NetworkResultCall(call)
    }

}
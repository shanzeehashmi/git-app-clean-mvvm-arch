package com.zeeshan.gitapp.data.data_source.remote.network.error

import com.google.gson.annotations.SerializedName

class NetworkErrorBody {
    @SerializedName("message")
    var errorMessage: String? = null
}
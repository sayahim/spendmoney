package com.himorfosis.kelolabelanja.network.wrapper

import com.google.gson.annotations.SerializedName

data class ResponseWrapper<T> (
        @SerializedName("status")
        val status: T,
        @SerializedName("data")
        val data: T
)
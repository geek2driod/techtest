package com.techtest.data.network.model

import com.google.gson.annotations.SerializedName

data class CoinTagNetworkModel(
    @SerializedName("id")
    val id: String,

    @SerializedName("name")
    val name: String
)
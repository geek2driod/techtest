package com.techtest.data.network.model

import com.google.gson.annotations.SerializedName

data class CoinNetworkModel(
    @SerializedName("id")
    val id: String,

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("symbol")
    val symbol: String? = null,

    @SerializedName("logo")
    val logo: String? = null,

    @SerializedName("tags")
    val tags: List<CoinTagNetworkModel>? = null,

    @SerializedName("description")
    val description: String? = null
)
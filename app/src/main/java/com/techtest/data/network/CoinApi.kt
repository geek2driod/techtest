package com.techtest.data.network

import com.techtest.common.NetworkConstants
import com.techtest.data.network.model.CoinNetworkModel
import retrofit2.http.GET
import retrofit2.http.Path

interface CoinApi {
    @GET(NetworkConstants.COIN_PAPRIKA_FEED)
    suspend fun getCoins(): List<CoinNetworkModel>

    @GET(NetworkConstants.COIN_PAPRIKA_FEED_ID)
    suspend fun getCoin(@Path("id") id: String): CoinNetworkModel
}
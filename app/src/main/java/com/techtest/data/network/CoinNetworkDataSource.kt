package com.techtest.data.network

import com.techtest.data.network.model.CoinNetworkModel
import com.techtest.utils.Result

interface CoinNetworkDataSource {
    suspend fun getCoins(): Result<List<CoinNetworkModel>>
    suspend fun getCoin(id: String): Result<CoinNetworkModel>
}
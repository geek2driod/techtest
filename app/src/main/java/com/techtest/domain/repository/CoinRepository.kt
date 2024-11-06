package com.techtest.domain.repository

import com.techtest.domain.model.Coin
import com.techtest.utils.Result

interface CoinRepository {
    suspend fun getCoins(force: Boolean, sorted: Boolean): Result<List<Coin>>
    suspend fun getCoin(id: String, force: Boolean): Result<Coin>
}
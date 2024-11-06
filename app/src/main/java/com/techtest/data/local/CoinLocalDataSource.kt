package com.techtest.data.local

import com.techtest.data.local.model.CoinLocalModel

interface CoinLocalDataSource {
    suspend fun getCoins(): List<CoinLocalModel>
    suspend fun getCoin(id: String): CoinLocalModel?
    suspend fun insert(coins: List<CoinLocalModel>)
    suspend fun deleteAll()
}
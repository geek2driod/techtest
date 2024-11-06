package com.techtest.data.local

import com.techtest.data.local.model.CoinLocalModel

class CoinLocalDataSourceImpl(
    private val dao: CoinDao
) : CoinLocalDataSource {
    override suspend fun getCoins(): List<CoinLocalModel> {
        return dao.getCoins()
    }

    override suspend fun getCoin(id: String): CoinLocalModel? {
        return dao.getCoinById(id)
    }

    override suspend fun insert(coins: List<CoinLocalModel>) {
        dao.insert(coins)
    }

    override suspend fun deleteAll() {
        dao.deleteAll()
    }
}
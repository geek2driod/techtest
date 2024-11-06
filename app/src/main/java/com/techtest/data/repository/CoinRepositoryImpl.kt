package com.techtest.data.repository

import com.techtest.data.local.CoinLocalDataSource
import com.techtest.data.mapper.CoinModelMapper
import com.techtest.data.network.CoinNetworkDataSource
import com.techtest.domain.model.Coin
import com.techtest.domain.repository.CoinRepository
import com.techtest.utils.AppCoroutineDispatchers
import com.techtest.utils.Result
import kotlinx.coroutines.withContext

class CoinRepositoryImpl(
    private val network: CoinNetworkDataSource,
    private val local: CoinLocalDataSource,
    private val dispatchers: AppCoroutineDispatchers,
    private val mapper: CoinModelMapper
) : CoinRepository {
    override suspend fun getCoins(force: Boolean, sorted: Boolean): Result<List<Coin>> {
        return withContext(dispatchers.default) {
            val localCoins = local.getCoins()
            val localMappedCoins = localCoins.map { mapper.localToDomain(it) }
            val sortedCoins = if (sorted) sortCoinsByName(localMappedCoins) else localMappedCoins

            if (sortedCoins.isNotEmpty() && !force) {
                return@withContext Result.Success(sortedCoins)
            }

            when (val response = network.getCoins()) {
                is Result.Success -> {
                    val coins = response.data.map { mapper.networkToDomain(it) }
                    val sortedCoinsByName = if (sorted) sortCoinsByName(coins) else coins

                    coins.map { mapper.domainToLocal(it) }.let {
                        local.insert(it)
                    }

                    Result.Success(sortedCoinsByName)
                }

                else -> {
                    if (sortedCoins.isNotEmpty()) return@withContext Result.Success(sortedCoins)
                    Result.Error(message = (response as Result.Error).message)
                }
            }
        }
    }

    override suspend fun getCoin(id: String, force: Boolean): Result<Coin> {
        return withContext(dispatchers.default) {
            val localCoin = local.getCoin(id)
            val localMappedCoin = localCoin?.let { mapper.localToDomain(it) }

            if (localMappedCoin != null && !force) {
                return@withContext Result.Success(localMappedCoin)
            }

            when (val response = network.getCoin(id)) {
                is Result.Success -> {
                    val coin = mapper.networkToDomain(response.data)
                    mapper.domainToLocal(coin).let {
                        local.insert(listOf(it))
                    }

                    Result.Success(coin)
                }

                else -> {
                    if (localMappedCoin != null) return@withContext Result.Success(localMappedCoin)
                    Result.Error(message = (response as Result.Error).message)
                }
            }
        }
    }

    // This will sort coins by name
    private fun sortCoinsByName(coins: List<Coin>): List<Coin> {
        return coins.sortedBy { it.name }
    }
}
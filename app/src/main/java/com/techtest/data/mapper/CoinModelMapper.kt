package com.techtest.data.mapper

import com.techtest.data.local.model.CoinLocalModel
import com.techtest.data.local.model.CoinTagLocalModel
import com.techtest.data.network.model.CoinNetworkModel
import com.techtest.data.network.model.CoinTagNetworkModel
import com.techtest.domain.model.Coin
import com.techtest.domain.model.CoinTag

interface CoinModelMapper {
    fun localToDomain(local: CoinLocalModel): Coin
    fun networkToDomain(network: CoinNetworkModel): Coin
    fun domainToLocal(domain: Coin): CoinLocalModel

    fun localTagToDomain(local: CoinTagLocalModel): CoinTag
    fun networkTagToDomain(network: CoinTagNetworkModel): CoinTag
    fun domainTagToLocal(domain: CoinTag): CoinTagLocalModel
}
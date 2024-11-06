package com.techtest.data.mapper

import com.techtest.data.local.model.CoinLocalModel
import com.techtest.data.local.model.CoinTagLocalModel
import com.techtest.data.network.model.CoinNetworkModel
import com.techtest.data.network.model.CoinTagNetworkModel
import com.techtest.domain.model.Coin
import com.techtest.domain.model.CoinTag
import java.util.ArrayList

class CoinModelMapperImpl : CoinModelMapper {
    override fun localToDomain(local: CoinLocalModel): Coin {
        return Coin(
            id = local.id,
            name = local.name,
            symbol = local.symbol,
            logo = local.logo,
            tags = local.tags.orEmpty().map { localTagToDomain(it) },
            description = local.description
        )
    }

    override fun networkToDomain(network: CoinNetworkModel): Coin {
        return Coin(
            id = network.id,
            name = network.name.orEmpty(),
            symbol = network.symbol.orEmpty(),
            logo = network.logo.orEmpty(),
            tags = network.tags.orEmpty().map { networkTagToDomain(it) },
            description = network.description.orEmpty()
        )
    }

    override fun domainToLocal(domain: Coin): CoinLocalModel {
        return CoinLocalModel(
            id = domain.id,
            name = domain.name,
            symbol = domain.symbol,
            logo = domain.logo,
            tags = ArrayList(domain.tags.map { domainTagToLocal(it) }),
            description = domain.description
        )
    }

    override fun localTagToDomain(local: CoinTagLocalModel): CoinTag {
        return CoinTag(
            id = local.id,
            name = local.name
        )
    }

    override fun networkTagToDomain(network: CoinTagNetworkModel): CoinTag {
        return CoinTag(
            id = network.id,
            name = network.name
        )
    }

    override fun domainTagToLocal(domain: CoinTag): CoinTagLocalModel {
        return CoinTagLocalModel(
            id = domain.id,
            name = domain.name
        )
    }
}
package com.techtest.repository

import com.google.common.truth.Truth.assertThat
import com.techtest.data.local.CoinLocalDataSource
import com.techtest.data.local.model.CoinLocalModel
import com.techtest.data.mapper.CoinModelMapper
import com.techtest.data.mapper.CoinModelMapperImpl
import com.techtest.data.network.CoinNetworkDataSource
import com.techtest.data.network.model.CoinNetworkModel
import com.techtest.data.repository.CoinRepositoryImpl
import com.techtest.domain.model.Coin
import com.techtest.domain.repository.CoinRepository
import com.techtest.utils.Result
import com.techtest.utils.TestAppCoroutineDispatchers
import com.techtest.utils.UiMessage
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.mock
import org.mockito.BDDMockito.verify
import org.mockito.Mockito.times

class CoinRepositoryTest {
    private val network: CoinNetworkDataSource = mock(CoinNetworkDataSource::class.java)
    private val local: CoinLocalDataSource = mock(CoinLocalDataSource::class.java)
    private val dispatchers = TestAppCoroutineDispatchers.create()
    private val mapper: CoinModelMapper = CoinModelMapperImpl()

    private val sut: CoinRepository = CoinRepositoryImpl(
        network = network,
        local = local,
        dispatchers = dispatchers,
        mapper = mapper
    )

    @Test
    fun `given_local_coins_from_database_when_get_coins_then_returns_success_and_coins_from_database`() = runTest {
        given(local.getCoins()).willReturn(localCoins)

        val result = sut.getCoins(force = false, sorted = false)

        verify(network, times(0)).getCoins()
        verify(local, times(1)).getCoins()

        assertThat(result.isSuccess()).isTrue()
        assertThat((result as Result.Success).data).isEqualTo(domainCoins)
    }

    @Test
    fun `given_force_when_get_coins_then_get_coins_from_network`() = runTest {
        given(local.getCoins()).willReturn(localCoins)
        given(network.getCoins()).willReturn(Result.Success(networkCoins))

        sut.getCoins(force = true, sorted = true)

        verify(network, times(1)).getCoins()
    }

    @Test
    fun `given_failure_from_network_and_nothing_from_db_when_get_coins_then_returns_error`() = runTest {
        val errorResult = Result.Error<List<CoinNetworkModel>>(message = UiMessage.StringType("Internet Error"))

        given(local.getCoins()).willReturn(emptyList())
        given(network.getCoins()).willReturn(errorResult)

        val result = sut.getCoins(force = true, sorted = true)
        assertThat(result).isEqualTo(Result.Error<List<Coin>>(message = errorResult.message))
    }

    @Test
    fun `given_failure_from_network_and_coins_from_db_when_get_coins_then_returns_success`() = runTest {
        val errorResult = Result.Error<List<CoinNetworkModel>>(message = UiMessage.StringType("Internet Error"))

        given(local.getCoins()).willReturn(localCoins)
        given(network.getCoins()).willReturn(errorResult)

        val result = sut.getCoins(force = true, sorted = false)
        assertThat(result.isSuccess()).isTrue()
        assertThat((result as Result.Success).data).isEqualTo(domainCoins)
    }

    @Test
    fun `given_local_coin_from_database_when_get_coin_then_returns_success_and_coin_from_database`() = runTest {
        val coinId = localCoin1.id
        given(local.getCoin(coinId)).willReturn(localCoin1)

        val result = sut.getCoin(id = localCoin1.id, force = false)

        verify(local, times(1)).getCoin(coinId)
        verify(network, times(0)).getCoin(coinId)

        assertThat(result.isSuccess()).isTrue()
        assertThat((result as Result.Success).data).isEqualTo(domainCoin1)
    }

    @Test
    fun `given_force_when_get_coin_then_get_coin_from_network`() = runTest {
        val coinId = localCoin1.id
        given(local.getCoin(coinId)).willReturn(localCoin1)
        given(network.getCoin(coinId)).willReturn(Result.Success(networkCoin1))

        val result = sut.getCoin(id = localCoin1.id, force = true)

        verify(network, times(1)).getCoin(coinId)

        assertThat(result.isSuccess()).isTrue()
        assertThat((result as Result.Success).data).isEqualTo(domainCoin1)
    }

    @Test
    fun `given_failure_from_network_and_coin_from_db_when_get_coin_then_returns_success`() = runTest {
        val coinId = localCoin1.id
        val errorResult = Result.Error<CoinNetworkModel>(message = UiMessage.StringType("Internet Error"))

        given(local.getCoin(coinId)).willReturn(localCoin1)
        given(network.getCoin(coinId)).willReturn(errorResult)

        val result = sut.getCoin(id = localCoin1.id, force = true)
        assertThat(result.isSuccess()).isTrue()
        assertThat((result as Result.Success).data).isEqualTo(domainCoin1)
    }

    @Test
    fun `given_failure_from_network_and_nothing_from_db_when_get_coin_then_returns_failure`() = runTest {
        val coinId = localCoin1.id
        val errorResult = Result.Error<CoinNetworkModel>(message = UiMessage.StringType("Internet Error"))

        given(local.getCoin(coinId)).willReturn(null)
        given(network.getCoin(coinId)).willReturn(errorResult)

        val result = sut.getCoin(id = localCoin1.id, force = true)
        assertThat(result.isSuccess()).isFalse()
        assertThat(result).isEqualTo(Result.Error<List<Coin>>(message = errorResult.message))
    }

    @Test
    fun `given_coins_and_sorted_true_when_get_coins_then_returns_sorted_coins_by_name`() = runTest {
        given(local.getCoins()).willReturn(localCoins)
        given(network.getCoins()).willReturn(Result.Success(networkCoins))

        val result = sut.getCoins(force = false, sorted = true)
        assertThat(result.isSuccess()).isTrue()
        assertThat((result as Result.Success).data).isEqualTo(domainCoins.sortedBy { it.name })
    }

    companion object {
        private val localCoin1 = CoinLocalModel(
            id = "btc",
            name = "Bitcoin",
            symbol = "Btc",
            logo = "https://www.bitcoin.com",
            tags = arrayListOf(),
            description = "Bitcoin Internet"
        )

        private val localCoin2 = CoinLocalModel(
            id = "atc",
            name = "Aitcoin",
            symbol = "Atc",
            logo = "https://www.aitcoin.com",
            tags = arrayListOf(),
            description = "Aitcoin Internet"
        )

        private val networkCoin1 = CoinNetworkModel(
            id = "btc",
            name = "Bitcoin",
            symbol = "Btc",
            logo = "https://www.bitcoin.com",
            tags = listOf(),
            description = "Bitcoin Internet"
        )

        private val networkCoin2 = CoinNetworkModel(
            id = "atc",
            name = "Aitcoin",
            symbol = "Atc",
            logo = "https://www.aitcoin.com",
            tags = listOf(),
            description = "Aitcoin Internet"
        )

        private val domainCoin1 = Coin(
            id = "btc",
            name = "Bitcoin",
            symbol = "Btc",
            logo = "https://www.bitcoin.com",
            tags = listOf(),
            description = "Bitcoin Internet"
        )

        private val domainCoin2 = Coin(
            id = "atc",
            name = "Aitcoin",
            symbol = "Atc",
            logo = "https://www.aitcoin.com",
            tags = listOf(),
            description = "Aitcoin Internet"
        )

        private val domainCoins = listOf(domainCoin1, domainCoin2)
        private val networkCoins = listOf(networkCoin1, networkCoin2)
        private val localCoins = listOf(localCoin1, localCoin2)
    }
}
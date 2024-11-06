package com.techtest.viewmodel

import com.google.common.truth.Truth.assertThat
import com.techtest.domain.model.Coin
import com.techtest.domain.usecase.GetCoinsUsecase
import com.techtest.presentation.list.CoinListState
import com.techtest.presentation.list.CoinListViewModel
import com.techtest.utils.MainCoroutineRule
import com.techtest.utils.Result
import com.techtest.utils.UiMessage
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.Mockito.mock

class CoinListViewModelTest {
    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val getCoinsUsecase: GetCoinsUsecase = mock(GetCoinsUsecase::class.java)

    private val sut = CoinListViewModel(
        getCoinsUsecase = getCoinsUsecase
    )

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `given_loading_when_get_coins_then_returns_loading_state`() = runTest {
        val flow = flow<Result<List<Coin>>> { emit(Result.Loading()) }
        given(getCoinsUsecase(force = false)).willReturn(flow)

        sut.load()
        advanceUntilIdle()

        assertThat(sut.state.value).isEqualTo(CoinListState.Loading)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `given_success_when_get_coins_then_returns_success_state`() = runTest {
        val flow = flow<Result<List<Coin>>> { emit(Result.Success(coins)) }
        given(getCoinsUsecase(force = false)).willReturn(flow)

        sut.load()
        advanceUntilIdle()

        assertThat(sut.state.value).isEqualTo(CoinListState.Success(coins))
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `given_failure_when_get_coins_then_returns_failure_state`() = runTest {
        val error = Result.Error<List<Coin>>(UiMessage.StringType("Error"))
        val flow = flow<Result<List<Coin>>> { emit(error) }
        given(getCoinsUsecase(force = false)).willReturn(flow)

        sut.load()
        advanceUntilIdle()

        assertThat(sut.state.value).isEqualTo(CoinListState.Error(error.message))
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `given_refresh_when_refresh_coins_then_returns_success_state`() = runTest {
        val flow = flow<Result<List<Coin>>> { emit(Result.Success(coins)) }
        given(getCoinsUsecase(force = true)).willReturn(flow)

        sut.onRefreshCoins()
        advanceUntilIdle()

        assertThat(sut.state.value).isEqualTo(CoinListState.Success(coins))
    }

    companion object {
        private val coin = Coin(
            id = "btc",
            name = "Bitcoin",
            symbol = "Btc",
            logo = "https://www.bitcoin.com",
            tags = listOf(),
            description = "Bitcoin Internet"
        )

        private val coins = listOf(coin)
    }
}

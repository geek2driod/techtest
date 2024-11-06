package com.techtest.presentation.list

import androidx.compose.runtime.Stable
import com.techtest.domain.model.Coin
import com.techtest.utils.UiMessage

@Stable
sealed interface CoinListState {
    data class Success(val coins: List<Coin>) : CoinListState
    object Loading : CoinListState
    data class Error(val uiMessage: UiMessage) : CoinListState
}
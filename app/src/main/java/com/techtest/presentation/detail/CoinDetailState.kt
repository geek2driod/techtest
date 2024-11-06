package com.techtest.presentation.detail

import androidx.compose.runtime.Stable
import com.techtest.domain.model.Coin
import com.techtest.utils.UiMessage

@Stable
sealed interface CoinDetailState {
    data class Success(val coin: Coin) : CoinDetailState
    object Loading : CoinDetailState
    class Error(val uiMessage: UiMessage) : CoinDetailState
}
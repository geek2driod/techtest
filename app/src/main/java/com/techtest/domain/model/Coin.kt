package com.techtest.domain.model

import androidx.compose.runtime.Stable

@Stable
data class Coin(
    val id: String = "",
    val name: String = "",
    val symbol: String = "",
    val logo: String = "",
    val tags: List<CoinTag> = emptyList(),
    val description: String = ""
)
package com.roman.cryptotracker.crypto.presentation.coin_list

import androidx.compose.runtime.Immutable
import com.roman.cryptotracker.crypto.presentation.models.CoinUiModel

@Immutable
data class CoinListUiState(
    val isLoading: Boolean = false,
    val coins: List<CoinUiModel> = emptyList(),
    val selectedCoin: CoinUiModel? = null,
)
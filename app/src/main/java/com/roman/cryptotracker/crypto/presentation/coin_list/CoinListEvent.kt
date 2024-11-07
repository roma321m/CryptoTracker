package com.roman.cryptotracker.crypto.presentation.coin_list

import com.roman.cryptotracker.crypto.presentation.models.CoinUiModel

sealed interface CoinListEvent {
    data class OnCoinClick(val coinUiModel: CoinUiModel) : CoinListEvent
    data object OnRefresh : CoinListEvent
}
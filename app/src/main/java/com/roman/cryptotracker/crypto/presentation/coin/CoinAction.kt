package com.roman.cryptotracker.crypto.presentation.coin

import com.roman.cryptotracker.crypto.presentation.models.CoinUiModel

sealed interface CoinAction {
    data class OnCoinClick(val coinUiModel: CoinUiModel) : CoinAction
    data object OnRefresh : CoinAction
}
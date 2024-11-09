package com.roman.cryptotracker.crypto.presentation.coin_list

import com.roman.cryptotracker.crypto.presentation.models.CoinUiModel

sealed interface CoinListAction {
    data class OnCoinClick(val coinUiModel: CoinUiModel) : CoinListAction
    data object OnRefresh : CoinListAction
}
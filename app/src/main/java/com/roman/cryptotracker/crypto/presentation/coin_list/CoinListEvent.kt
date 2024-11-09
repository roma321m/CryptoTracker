package com.roman.cryptotracker.crypto.presentation.coin_list

import com.roman.cryptotracker.core.domain.util.NetworkError

sealed interface CoinListEvent {
    data class Error(val error: NetworkError) : CoinListEvent
}
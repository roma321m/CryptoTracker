package com.roman.cryptotracker.crypto.presentation.coin

import com.roman.cryptotracker.core.domain.util.NetworkError

sealed interface CoinEvent {
    data class Error(val error: NetworkError) : CoinEvent
}
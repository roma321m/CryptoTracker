package com.roman.cryptotracker.crypto.domain

import com.roman.cryptotracker.core.domain.util.NetworkError
import com.roman.cryptotracker.core.domain.util.Result

interface CoinRepository {
    suspend fun getCoins(): Result<List<Coin>, NetworkError>
}
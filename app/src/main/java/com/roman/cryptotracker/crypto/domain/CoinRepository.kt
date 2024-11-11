package com.roman.cryptotracker.crypto.domain

import com.roman.cryptotracker.core.domain.util.NetworkError
import com.roman.cryptotracker.core.domain.util.Result
import java.time.ZonedDateTime

interface CoinRepository {
    suspend fun getCoins(): Result<List<Coin>, NetworkError>
    suspend fun getCoinHistory(
        coinId: String,
        start: ZonedDateTime,
        end: ZonedDateTime,
    ): Result<List<CoinPrice>, NetworkError>
}
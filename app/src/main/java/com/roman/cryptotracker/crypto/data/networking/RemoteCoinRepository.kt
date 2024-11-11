package com.roman.cryptotracker.crypto.data.networking

import com.roman.cryptotracker.core.data.networking.constructUrl
import com.roman.cryptotracker.core.data.networking.safeCall
import com.roman.cryptotracker.core.domain.util.NetworkError
import com.roman.cryptotracker.core.domain.util.Result
import com.roman.cryptotracker.core.domain.util.map
import com.roman.cryptotracker.crypto.data.mappers.toCoin
import com.roman.cryptotracker.crypto.data.mappers.toCoinPrice
import com.roman.cryptotracker.crypto.data.mappers.toUtcMilli
import com.roman.cryptotracker.crypto.data.networking.dto.CoinHistoryDto
import com.roman.cryptotracker.crypto.data.networking.dto.CoinsResponseDto
import com.roman.cryptotracker.crypto.domain.Coin
import com.roman.cryptotracker.crypto.domain.CoinPrice
import com.roman.cryptotracker.crypto.domain.CoinRepository
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import java.time.ZonedDateTime

class RemoteCoinRepository(
    private val httpClient: HttpClient
) : CoinRepository {

    override suspend fun getCoins(): Result<List<Coin>, NetworkError> {
        return safeCall<CoinsResponseDto> {
            httpClient.get(
                urlString = constructUrl("/assets")
            )
        }.map { response ->
            response.data.map { dto ->
                dto.toCoin()
            }
        }
    }

    override suspend fun getCoinHistory(
        coinId: String,
        start: ZonedDateTime,
        end: ZonedDateTime
    ): Result<List<CoinPrice>, NetworkError> {
        return safeCall<CoinHistoryDto> {
            httpClient.get(
                urlString = constructUrl("/assets/$coinId/history")
            ) {
                parameter("interval", "h6")
                parameter("start", start.toUtcMilli())
                parameter("end", end.toUtcMilli())
            }
        }.map { response ->
            response.data.map { dto ->
                dto.toCoinPrice()
            }
        }
    }

}
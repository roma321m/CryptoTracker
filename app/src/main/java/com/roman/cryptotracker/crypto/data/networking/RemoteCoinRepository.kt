package com.roman.cryptotracker.crypto.data.networking

import com.roman.cryptotracker.core.data.networking.constructUrl
import com.roman.cryptotracker.core.data.networking.safeCall
import com.roman.cryptotracker.core.domain.util.NetworkError
import com.roman.cryptotracker.core.domain.util.Result
import com.roman.cryptotracker.core.domain.util.map
import com.roman.cryptotracker.crypto.data.mappers.toCoin
import com.roman.cryptotracker.crypto.data.networking.dto.CoinsResponseDto
import com.roman.cryptotracker.crypto.domain.Coin
import com.roman.cryptotracker.crypto.domain.CoinRepository
import io.ktor.client.HttpClient
import io.ktor.client.request.get

class RemoteCoinRepository(
    private val httpClient: HttpClient
) : CoinRepository {

    override suspend fun getCoins(): Result<List<Coin>, NetworkError> {
        return safeCall<CoinsResponseDto> {
            httpClient.get(
                urlString = constructUrl("/assets")
            )
        }.map { response ->
            response.data.map { coinDto ->
                coinDto.toCoin()
            }
        }
    }

}
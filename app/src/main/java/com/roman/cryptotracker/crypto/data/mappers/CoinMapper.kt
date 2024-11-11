package com.roman.cryptotracker.crypto.data.mappers

import com.roman.cryptotracker.crypto.data.networking.dto.CoinDto
import com.roman.cryptotracker.crypto.data.networking.dto.CoinPriceDto
import com.roman.cryptotracker.crypto.domain.Coin
import com.roman.cryptotracker.crypto.domain.CoinPrice

fun CoinDto.toCoin(): Coin {
    return Coin(
        id = id,
        name = name,
        rank = rank,
        symbol = symbol,
        marketCapUsd = marketCapUsd,
        priceUsd = priceUsd,
        changePercent24Hr = changePercent24Hr
    )
}

fun CoinPriceDto.toCoinPrice(): CoinPrice {
    return CoinPrice(
        priceUsd = priceUsd,
        dateTime = time.toZonedDateTime()
    )
}
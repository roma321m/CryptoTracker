package com.roman.cryptotracker.crypto.presentation.models

import androidx.annotation.DrawableRes
import com.roman.cryptotracker.core.presentation.components.graph.DataPoint
import com.roman.cryptotracker.core.presentation.util.getDrawableIdForCoin
import com.roman.cryptotracker.crypto.domain.Coin

data class CoinUiModel(
    val id: String,
    val rank: Int,
    val name: String,
    val symbol: String,
    val marketCapUsd: DisplayableNumber,
    val priceUsd: DisplayableNumber,
    val changePercent24Hr: DisplayableNumber,
    @DrawableRes val iconRes: Int,
    val coinPriceHistory: List<DataPoint> = emptyList(),
)

fun Coin.toCoinUiModel() = CoinUiModel(
    id = id,
    rank = rank,
    name = name,
    symbol = symbol,
    marketCapUsd = marketCapUsd.toDisplayableNumber(),
    priceUsd = priceUsd.toDisplayableNumber(),
    changePercent24Hr = changePercent24Hr.toDisplayableNumber(),
    iconRes = getDrawableIdForCoin(symbol)
)
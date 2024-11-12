package com.roman.cryptotracker.ui.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.roman.cryptotracker.core.presentation.components.graph.DataPoint
import com.roman.cryptotracker.core.presentation.util.getDrawableIdForCoin
import com.roman.cryptotracker.crypto.domain.CoinPrice
import com.roman.cryptotracker.crypto.presentation.coin.CoinUiState
import com.roman.cryptotracker.crypto.presentation.models.CoinUiModel
import com.roman.cryptotracker.crypto.presentation.models.toDisplayableNumber
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import kotlin.random.Random

class CoinUiStatePreviewParameterProvider : PreviewParameterProvider<CoinUiState> {

    private val dataPoints = (1..20).map {
        CoinPrice(
            Random.nextFloat() * 1_000.0,
            ZonedDateTime.now().plusHours(it.toLong())
        )
    }.map {
        DataPoint(
            x = it.dateTime.hour.toFloat(),
            y = it.priceUsd.toFloat(),
            xLabel = DateTimeFormatter.ofPattern("hh\nM/d").format(it.dateTime),
        )
    }

    private val coins = listOf(
        CoinUiModel(
            id = "bitcoin",
            name = "Bitcoin",
            symbol = "BTC",
            rank = 1,
            marketCapUsd = 380000000.0.toDisplayableNumber(),
            priceUsd = 68458.15.toDisplayableNumber(),
            changePercent24Hr = 2.5.toDisplayableNumber(),
            iconRes = getDrawableIdForCoin("BTC"),
            coinPriceHistory = dataPoints
        ),
        CoinUiModel(
            id = "ethereum",
            name = "Ethereum",
            symbol = "ETH",
            rank = 2,
            marketCapUsd = 180000000.0.toDisplayableNumber(),
            priceUsd = 3200.50.toDisplayableNumber(),
            changePercent24Hr = (-1.2).toDisplayableNumber(),
            iconRes = getDrawableIdForCoin("ETH"),
            coinPriceHistory = dataPoints
        ),
        CoinUiModel(
            id = "tether",
            name = "Tether",
            symbol = "USDT",
            rank = 3,
            marketCapUsd = 70000000.0.toDisplayableNumber(),
            priceUsd = 1.00.toDisplayableNumber(),
            changePercent24Hr = 0.1.toDisplayableNumber(),
            iconRes = getDrawableIdForCoin("USDT")
        ),
        CoinUiModel(
            id = "binancecoin",
            name = "Binance Coin",
            symbol = "BNB",
            rank = 4,
            marketCapUsd = 50000000.0.toDisplayableNumber(),
            priceUsd = 450.25.toDisplayableNumber(),
            changePercent24Hr = 3.8.toDisplayableNumber(),
            iconRes = getDrawableIdForCoin("BNB")
        ),
        CoinUiModel(
            id = "usdcoin",
            name = "USD Coin",
            symbol = "USDC",
            rank = 5,
            marketCapUsd = 45000000.0.toDisplayableNumber(),
            priceUsd = 1.00.toDisplayableNumber(),
            changePercent24Hr = 0.0.toDisplayableNumber(),
            iconRes = getDrawableIdForCoin("USDC")
        )
    )

    private val loadingUiState = CoinUiState(isLoading = true)
    private val listUiState = CoinUiState(coins = coins)
    private val emptyListUiState = CoinUiState()
    private val positiveSelectedCoinUiState = CoinUiState(coins = coins, selectedCoin = coins[0])
    private val negativeSelectedCoinUiState = CoinUiState(coins = coins, selectedCoin = coins[1])

    override val values: Sequence<CoinUiState> = sequenceOf(
        loadingUiState,
        listUiState,
        emptyListUiState,
        positiveSelectedCoinUiState,
        negativeSelectedCoinUiState
    )
}
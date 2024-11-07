package com.roman.cryptotracker.ui.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.roman.cryptotracker.core.presentation.util.getDrawableIdForCoin
import com.roman.cryptotracker.crypto.presentation.coin_list.CoinListUiState
import com.roman.cryptotracker.crypto.presentation.models.CoinUiModel
import com.roman.cryptotracker.crypto.presentation.models.toDisplayableNumber

class CoinListUiStatePreviewParameterProvider : PreviewParameterProvider<CoinListUiState> {

    private val coins = listOf(
        CoinUiModel(
            id = "bitcoin",
            name = "Bitcoin",
            symbol = "BTC",
            rank = 1,
            marketCapUsd = 380000000.0.toDisplayableNumber(),
            priceUsd = 68458.15.toDisplayableNumber(),
            changePercent24Hr = 2.5.toDisplayableNumber(),
            iconRes = getDrawableIdForCoin("BTC")
        ),
        CoinUiModel(
            id = "ethereum",
            name = "Ethereum",
            symbol = "ETH",
            rank = 2,
            marketCapUsd = 180000000.0.toDisplayableNumber(),
            priceUsd = 3200.50.toDisplayableNumber(),
            changePercent24Hr = (-1.2).toDisplayableNumber(),
            iconRes = getDrawableIdForCoin("ETH")
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

    private val loadingUiState = CoinListUiState(isLoading = true)
    private val listUiState = CoinListUiState(coins = coins)
    private val emptyListUiState = CoinListUiState()
    private val listWithSelectedCoinUiState =
        CoinListUiState(coins = coins, selectedCoin = coins[2])

    override val values: Sequence<CoinListUiState> = sequenceOf(
        loadingUiState,
        listUiState,
        emptyListUiState,
        listWithSelectedCoinUiState
    )
}
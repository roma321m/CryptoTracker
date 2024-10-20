package com.roman.cryptotracker.ui.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.roman.cryptotracker.crypto.presentation.models.CoinUiModel
import com.roman.cryptotracker.crypto.presentation.models.DisplayableNumber
import com.roman.cryptotracker.crypto.presentation.models.toDisplayableNumber
import com.roman.cryptotracker.util.getDrawableIdForCoin

class CoinUiModelPreviewParameterProvider : PreviewParameterProvider<CoinUiModel> {

    private val bitcoinPositive = CoinUiModel(
        id = "bitcoin",
        rank = 1,
        name = "Bitcoin",
        symbol = "BTC",
        marketCapUsd = DisplayableNumber(
            380000000000.0,
            "$ 380,000,000,000"
        ),
        priceUsd = 62828.15.toDisplayableNumber(),
        changePercent24Hr = 1.0.toDisplayableNumber(),
        iconRes = getDrawableIdForCoin("BTC")
    )

    private val bitcoinNegative = bitcoinPositive.copy(
        changePercent24Hr = (-1.0).toDisplayableNumber()
    )

    override val values: Sequence<CoinUiModel> = sequenceOf(bitcoinNegative, bitcoinPositive)
}
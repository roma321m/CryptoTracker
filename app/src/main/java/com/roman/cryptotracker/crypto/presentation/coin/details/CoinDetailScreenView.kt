package com.roman.cryptotracker.crypto.presentation.coin.details

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.roman.cryptotracker.core.presentation.components.LoadingView
import com.roman.cryptotracker.crypto.presentation.coin.CoinUiState
import com.roman.cryptotracker.crypto.presentation.coin.details.components.CoinDetailView
import com.roman.cryptotracker.ui.preview.CoinUiStatePreviewParameterProvider
import com.roman.cryptotracker.ui.theme.CryptoTrackerTheme


@Composable
fun CoinDetailScreenView(
    uiState: CoinUiState,
    modifier: Modifier = Modifier,
) {
    if (uiState.isLoading) {
        LoadingView(modifier)
    } else {
        CoinDetailView(
            modifier = modifier,
            uiState = uiState,
        )
    }
}


@PreviewLightDark
@Composable
fun CoinDetailScreenViewPreview(
    @PreviewParameter(CoinUiStatePreviewParameterProvider::class) uiState: CoinUiState
) {
    CryptoTrackerTheme {
        Surface {
            CoinDetailScreenView(
                uiState = uiState
            )
        }
    }
}